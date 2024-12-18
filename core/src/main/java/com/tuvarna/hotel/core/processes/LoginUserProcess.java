package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.enums.RoleType;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.PasswordException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.login.LoginUserInput;
import com.tuvarna.hotel.api.models.login.LoginUserOperation;
import com.tuvarna.hotel.api.models.login.LoginUserOutput;
import com.tuvarna.hotel.core.exception.InputQueryPasswordExceptionCase;
import com.tuvarna.hotel.domain.encoder.MyPasswordEncoder;
import com.tuvarna.hotel.domain.encoder.PasswordEncoder;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.Optional;

@Singleton
public class LoginUserProcess extends BaseProcessor implements LoginUserOperation {
    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginUserProcess() {
        userRepository=SingletonManager.getInstance(UserRepositoryImpl.class);
        passwordEncoder=SingletonManager.getInstance(MyPasswordEncoder.class);
    }

    @Override
    public Either<ErrorProcessor, LoginUserOutput> process(LoginUserInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                UserEntity user =checkUserExistence(input);
                checkPasswordMatch(input.getPassword(), user);
                return LoginUserOutput.builder()
                        .message("Successfully logged user")
                        .role(RoleType.getByCode(user.getRole().name()))
                        .build();
                }).toEither()
                .mapLeft(InputQueryPasswordExceptionCase::handleThrowable));
    }

    private UserEntity checkUserExistence(LoginUserInput input) {
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(()->new QueryException("No user was found"));
    }
    private void checkPasswordMatch(String password, UserEntity user) {
        Optional.ofNullable(user)
                .filter(u -> passwordEncoder.matches(password, user.getHashedPassword()))
                .orElseThrow(() -> new PasswordException("Password is incorrect!"));
    }


}
