package Todo_List.TodoApp.CustomException;

import Todo_List.TodoApp.GlobalConstants;
import Todo_List.TodoApp.Models.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDTO> handleRuntimeException(
            RuntimeException exception) {

        ApiResponseDTO response = new ApiResponseDTO(
                exception.getMessage(),
                GlobalConstants.FAILED,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}