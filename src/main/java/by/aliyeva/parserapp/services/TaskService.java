package by.aliyeva.parserapp.services;

import by.aliyeva.parserapp.expressionEvaluator.ExpressionEvaluator;
import by.aliyeva.parserapp.models.Task;
import by.aliyeva.parserapp.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public String evaluateExpression(String expression) {
        try {
            return String.valueOf(ExpressionEvaluator.evaluateAndShowResult(expression));
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}
