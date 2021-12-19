package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.exception.StudentNotFoundException;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.model.StudentRepository;
import uk.ac.leedsbeckett.student.model.User;

@Component
public class ProfileService {

    private final PortalService portalService;
    private final StudentRepository studentRepository;

    public ProfileService(PortalService portalService, StudentRepository studentRepository) {
        this.portalService = portalService;
        this.studentRepository = studentRepository;
    }

    public ModelAndView getProfile(User user, Student student, String view) {
        return portalService.loadPortalUserDetails(user, student, view);
    }

    public ModelAndView editProfile(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
        ModelAndView modelAndView = new ModelAndView("profile-edit");
        modelAndView.addObject("student", student);
        return modelAndView;
    }

    public ModelAndView editProfile(Student student) {
        Student existingStudent = studentRepository.findById(student.getId()).orElseThrow(StudentNotFoundException::new);
        if (student.getForename() != null && !student.getForename().isEmpty()) {
            existingStudent.setForename(student.getForename());
        }
        if (student.getSurname() != null && !student.getSurname().isEmpty()) {
            existingStudent.setSurname(student.getSurname());
        }
        studentRepository.saveAndFlush(existingStudent);
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("student", existingStudent);
        modelAndView.addObject("isStudent", true);
        modelAndView.addObject("message", "Profile updated");
        return modelAndView;
    }
}