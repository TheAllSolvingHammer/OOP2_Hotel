package com.tuvarna.hotel.rest.controllers.base;


import javafx.scene.control.Alert;
import lombok.NoArgsConstructor;



@NoArgsConstructor
public abstract class BaseController {
//todo will add all cotnrollers ot be abstract

//    public <T extends OperationOutput> void handleOperation(
//            Either<ErrorProcessor, T> result,
//            SuccessHandler<T> successHandler) {
//
//        result.fold(
//
//                error -> {
//                    showErrorAlert(error.getMessage());
//                    return null;
//                },
//
//                success -> {
//                    successHandler.handle(success);
//                    return null;
//                }
//        );
//    }
//public void handleOperation(Either<ErrorProcessor, ? extends OperationOutput> result) {
//    return result.fold(
//            error -> {showErrorAlert(error.getMessage()); return null;},
//            success-> ResponseEntity.status(HttpStatus.OK.value()).body(success)
//    );
//
//}

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Operation Failed");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    @FunctionalInterface
    public interface SuccessHandler<T> {
        void handle(T result);
    }
}
