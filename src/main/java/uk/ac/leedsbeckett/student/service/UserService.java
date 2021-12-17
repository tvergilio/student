package uk.ac.leedsbeckett.student.service;

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

    public Student findStudentOrCreateFromUser(User user) {
        Student student = studentRepository.findByUserId(user.getId());
        if (student == null) {
            student = new Student();
            user.setStudent(student);
            userRepository.save(user);
        }
        return student;
    }
}
