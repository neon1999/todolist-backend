package Todo_List.TodoApp.Models;


import Todo_List.TodoApp.Tables.TodosTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class createTodoInputModel {

    public String task;
    public String date;
    public String day;
    public String weekStart;
    public String weekEnd;
    public TodoType todoType;
    public List<TodosTable> todos;
    public Long parentId;
    private boolean parentCheck;


}
