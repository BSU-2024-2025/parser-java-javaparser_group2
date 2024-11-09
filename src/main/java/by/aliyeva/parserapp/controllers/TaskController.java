package by.aliyeva.parserapp.controllers;

import by.aliyeva.parserapp.models.Task;
import by.aliyeva.parserapp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String listTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "taskPage";
    }

    @PostMapping("/save")
    public String saveTask(@RequestParam String name, @RequestParam String expression) {
        Task task = new Task();
        task.setName(name);
        task.setExpression(expression);
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/execute")
    public String executeTask(@RequestParam String name, @RequestParam String expression, Model model) {
        String result = taskService.evaluateExpression(expression);
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("result", result);
        model.addAttribute("taskName", name);
        model.addAttribute("expression", expression);
        return "taskPage";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam Long taskId) {
        taskService.deleteTaskById(taskId);
        return "redirect:/tasks";
    }

    @PostMapping("/load")
    public String loadTask(@RequestParam Long taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskName", task.getName());
        model.addAttribute("expression", task.getExpression());
        return "taskPage";
    }
}
