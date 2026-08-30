package Todo_List.TodoApp.Repositories;

import Todo_List.TodoApp.Tables.WeekTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface WeekRepository extends JpaRepository<WeekTable,Long> {
    Optional<WeekTable> findByWeekStart(Date weekStart);

    boolean existsByWeekStart(Date weekStart);
}
