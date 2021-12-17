package uk.ac.leedsbeckett.student.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.exception.CourseNotFoundException;
import uk.ac.leedsbeckett.student.model.*;

import java.util.List;

@Component
public class CourseService {

    private final CourseRepository courseRepository;
    private final PortalService portalService;
    private final UserService userService;
    private final EnrolmentRepository enrolmentRepository;

    public CourseService(CourseRepository courseRepository, PortalService portalService, UserService userService, EnrolmentRepository enrolmentRepository) {
        this.courseRepository = courseRepository;
        this.portalService = portalService;
        this.userService = userService;
        this.enrolmentRepository = enrolmentRepository;
    }

    public ModelAndView fetchCourses() {
        ModelAndView modelAndView = new ModelAndView("courses");
        List<Course> courses = courseRepository.findAll();
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

    public ModelAndView getCourse(Long id) throws ChangeSetPersister.NotFoundException {
        ModelAndView modelAndView = new ModelAndView("course");
        Course course = courseRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        modelAndView.addObject("course", course);
        return modelAndView;
    }

    public ModelAndView enrol(Long courseId) {
        User user = portalService.getLoggedInUser();
        if (user == null) {
            return new ModelAndView("redirect:/login");
        }
        Student student = userService.findStudentOrCreateFromUser(user);
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        ModelAndView modelAndView = new ModelAndView("course");
        Enrolment enrolment = createEnrolment(student, course);
        modelAndView.addObject("course", course);
        modelAndView.addObject("student", student);
        modelAndView.addObject("enrolment", enrolment);
        return modelAndView;
    }

    private Enrolment createEnrolment(Student student, Course course) {
        Enrolment enrolment = new Enrolment(student, course);
        return enrolmentRepository.save(enrolment);
    }
}
