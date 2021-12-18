package uk.ac.leedsbeckett.student.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.model.StudentRepository;
import uk.ac.leedsbeckett.student.model.User;
import uk.ac.leedsbeckett.student.model.UserRepository;

@Component
public class UserService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public UserService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            user = userRepository.findUserByUserName(authentication.getName());
        }
        return user;
    }

    public Student findStudentFromUser(User user) {
        return studentRepository.findByUserId(user.getId());
    }

    public void createStudentFromUser(User user) {
        Student student = new Student();
        user.setStudent(student);
        userRepository.saveAndFlush(user);
    }
}
