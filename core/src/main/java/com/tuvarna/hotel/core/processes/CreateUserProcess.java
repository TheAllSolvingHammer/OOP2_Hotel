package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.enums.RoleType;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.models.create.user.CreateUserInput;
import com.tuvarna.hotel.api.models.create.user.CreateUserOperation;
import com.tuvarna.hotel.api.models.create.user.CreateUserOutput;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.encoder.MyPasswordEncoder;
import com.tuvarna.hotel.domain.encoder.PasswordEncoder;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

@Singleton
public class CreateUserProcess extends BaseProcessor implements CreateUserOperation {

    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;
    public CreateUserProcess() {
        userRepository= SingletonManager.getInstance(UserRepositoryImpl.class);
        passwordEncoder=SingletonManager.getInstance(MyPasswordEncoder.class);
    }

    @Override
    public Either<ErrorProcessor, CreateUserOutput> process(CreateUserInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    System.out.println(input);
                    //

                    checkPasswordIntegrity(input.getPassword(),input.getPasswordSecond());
                checkRole(input.getRole());
                    UserEntity user= UserEntity.builder()
                            .firstName(input.getFirstName())
                            .lastName(input.getLastName())
                            .role(RoleEntity.getByCode(input.getRole().name()))
                            .hashedPassword(passwordEncoder.encode(input.getPassword()))
                            .username(input.getUsername())
                            .email(input.getEmail())
                            .phone(input.getPhone())
                            .build();
                    //
                    System.out.println(user);
                    //
                    userRepository.save(user);
                return CreateUserOutput.builder()
                        .message("Successfully added user")
                        .build();

                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
        }

    private void checkPasswordIntegrity(String password, String passwordSecond) {
        if(!password.equals(passwordSecond)){
            throw new InputException("Passwords doesnt match");
        }
    }

    private void checkRole(RoleType input) {
        if(RoleEntity.getByCode(input.toString()).equals(RoleEntity.UNKNOWN)){
            throw new InputException("Error in converting role");
        }
    }

}
