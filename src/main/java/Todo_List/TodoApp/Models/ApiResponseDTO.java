package Todo_List.TodoApp.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class ApiResponseDTO {
    public String message;
    public String status;
    public Object result;

    public ApiResponseDTO(String message, String status, Object result) {
        this.message = message;
        this.status = status;
        this.result = result;
    }
}
