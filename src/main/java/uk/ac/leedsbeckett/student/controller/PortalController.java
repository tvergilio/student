package uk.ac.leedsbeckett.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.service.PortalService;

@Controller
public class PortalController {

    private final PortalService portalService;

    public PortalController(PortalService portalService) {
        this.portalService = portalService;
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        return portalService.fetchStudentProfile("home");
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {
        return portalService.fetchStudentProfile("profile");
    }

}
