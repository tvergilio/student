package uk.ac.leedsbeckett.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.leedsbeckett.student.model.StudentRepository;

@Controller
public class PortalController {

    private final StudentRepository studentRepository;

    public PortalController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
    }

}
