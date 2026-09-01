package Todo_List.TodoApp.Repositories;

import Todo_List.TodoApp.Tables.DaysTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DayRespository extends JpaRepository<DaysTable,Long> {
    Optional<DaysTable> findByDate(String date);

    boolean existsByDate(String date);
}
