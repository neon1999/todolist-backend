package Todo_List.TodoApp.Tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DaysTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long dayId;
    public String day;
    public Date date;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "day" )
    public List<TodosTable> todos;
}
