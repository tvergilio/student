package uk.ac.leedsbeckett.student.controller;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.service.CourseService;

@Controller
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/courses")
    public ModelAndView courses() {
        return courseService.fetchCourses();
    }

    @RequestMapping("/courses/{id}")
    public ModelAndView showCourse(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return courseService.getCourse(id);
    }

    @RequestMapping("/courses/{id}/enrol")
    public ModelAndView enrolInCourse(@PathVariable Long id) {
        return courseService.enrol(id);
    }
}