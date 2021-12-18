package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.exception.CourseNotFoundException;
import uk.ac.leedsbeckett.student.model.*;

import java.util.List;

@Component
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrolmentService enrolmentService;
    private final UserService userService;
    private Student student;
    private Course course;

    public CourseService(CourseRepository courseRepository, UserService userService, EnrolmentService enrolmentService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.enrolmentService = enrolmentService;
    }

    public ModelAndView getCourses() {
        ModelAndView modelAndView = new ModelAndView("courses");
        List<Course> courses = courseRepository.findAll();
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

    public ModelAndView getCourse(Long id, User user) {
        populateStudentAndCourse(user, id);
        Enrolment existingEnrolment = enrolmentService.findEnrolment(course, student);
        return getModelAndView(existingEnrolment != null);
    }

    public ModelAndView enrolInCourse(Long id, User user) {
        populateStudentAndCourse(user, id);
        if (student == null) {
            userService.createStudentFromUser(user);
        }
        student = userService.findStudentFromUser(user);
        enrolmentService.createEnrolment(student, course);
        return getModelAndView(true);
    }

    private ModelAndView getModelAndView(boolean isEnrolled) {
        ModelAndView modelAndView = new ModelAndView("course");
        modelAndView.addObject("course", course);
        modelAndView.addObject("student", student);
        modelAndView.addObject("isEnrolled", isEnrolled);
        return modelAndView;
    }

    private void populateStudentAndCourse(User user, Long courseId) {
        student = userService.findStudentFromUser(user);
        course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
    }

}
