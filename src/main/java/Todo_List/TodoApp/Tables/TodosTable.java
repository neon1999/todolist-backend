package Todo_List.TodoApp.Tables;

import Todo_List.TodoApp.Models.TodoType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodosTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long todoId;
    public String task;
    public boolean completed;

    @Enumerated(EnumType.STRING)
    private TodoType todoType;

    private boolean parentCheck;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "groupId")
    private TodosTable parent;
    @OneToMany(mappedBy = "parent",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<TodosTable> children;

    @ManyToOne
    @JoinColumn(name = "day")
    @JsonIgnore
    public DaysTable day;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "week")
    public WeekTable week;


}
