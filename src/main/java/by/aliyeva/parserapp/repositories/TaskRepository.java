package by.aliyeva.parserapp.repositories;

import by.aliyeva.parserapp.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
