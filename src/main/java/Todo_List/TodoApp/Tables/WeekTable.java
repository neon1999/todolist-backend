package Todo_List.TodoApp.Tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeekTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String weekStart;
    public String weekEnd;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "week" )
    public List<TodosTable> todos;

}
