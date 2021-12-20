package uk.ac.leedsbeckett.student.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.student.exception.StudentAlreadyExistsException;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.model.StudentRepository;
import uk.ac.leedsbeckett.student.model.User;
import uk.ac.leedsbeckett.student.model.UserRepository;

@Component
public class UserService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    SecurityContext securityContext;

    public UserService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public User getLoggedInUser() {
        if (securityContext == null) {
            setSecurityContext(SecurityContextHolder.getContext());
        }
        Authentication authentication = securityContext.getAuthentication();
        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            user = userRepository.findUserByUserName(authentication.getName());
        }
        return user;
    }

    public Student findStudentFromUser(User user) {
        return studentRepository.findByUserId(user.getId());
    }

    public User createStudentFromUser(User user) {
        if (studentRepository.findByUserId(user.getId()) != null) {
            throw new StudentAlreadyExistsException(user.getUserName());
        }
        Student student = new Student();
        user.setStudent(student);
        return userRepository.saveAndFlush(user);
    }
}
