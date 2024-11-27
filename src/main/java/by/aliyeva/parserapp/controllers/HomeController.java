package by.aliyeva.parserapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToTasks() {
        return "redirect:/tasks";
    }
}
