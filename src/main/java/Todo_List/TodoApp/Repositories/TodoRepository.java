package Todo_List.TodoApp.Repositories;


import Todo_List.TodoApp.Tables.TodosTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodosTable,Long> {

}
