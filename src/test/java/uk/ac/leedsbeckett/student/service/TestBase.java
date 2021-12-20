package uk.ac.leedsbeckett.student.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.ac.leedsbeckett.student.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestBase {

    @MockBean
    protected CourseRepository courseRepository;
    @MockBean
    protected UserService userService;
    @MockBean
    protected EnrolmentService enrolmentService;
    @Autowired
    protected CourseService courseService;

    protected Course course1, course2, course3, course4, course5;
    protected List<Course> courseList;
    protected User userStudent;
    protected User userNotStudent;
    protected User userToBecomeStudent;
    protected Student student;
    protected Student createdStudent;
    protected Enrolment enrolment;

    @BeforeEach
    public void setUp() {
        createTestObjects();
        defineMockingBehaviour();
    }

    protected void createTestObjects() {
        String title = "Software Engineering for Service Computing";
        String description = "This module provides an in-depth look at the Service-Oriented Architecture paradigm and, more specifically, at its recent development: Microservices. You will gain theoretical knowledge of software design using a modular, loosely coupled approach, as well as practical experience with implementation tools and techniques highly valued in the industry.";
        Double fee = 999.99;
        course1 = new Course(title, description, fee);

        Faker faker = new Faker();
        course2 = new Course(faker.educator().course(), faker.lorem().paragraph(), faker.number().randomDouble(2, 0, 5000));
        course3 = new Course(faker.educator().course(), faker.lorem().paragraph(), faker.number().randomDouble(2, 0, 5000));
        course4 = new Course(faker.educator().course(), faker.lorem().paragraph(), faker.number().randomDouble(2, 0, 5000));
        course5 = new Course(faker.educator().course(), faker.lorem().paragraph(), faker.number().randomDouble(2, 0, 5000));

        course1.setId(1L);
        course2.setId(2L);
        course3.setId(3L);
        course4.setId(4L);
        course5.setId(5L);

        userStudent = new User(faker.name().username(), Role.STUDENT, "user@gmail.com", faker.animal().name());
        userNotStudent = new User(faker.name().username(), Role.STUDENT, "uns@gmail.com", faker.animal().name());
        userToBecomeStudent = new User(faker.name().username(), Role.STUDENT, "utbs@gmail.com", faker.animal().name());
        student = new Student(faker.name().firstName(), faker.name().lastName());
        createdStudent = new Student(faker.name().firstName(), faker.name().lastName());
        userStudent.setStudent(student);

        enrolment = new Enrolment(student, course2);
    }

    protected void defineMockingBehaviour() {
        courseList = Arrays.asList(course1, course2, course3, course4, course5);

        Mockito.when(courseRepository.findAll())
                .thenReturn(courseList);
        Mockito.when(courseRepository.findById(course1.getId()))
                .thenReturn(Optional.of(course1));
        Mockito.when(courseRepository.findById(course2.getId()))
                .thenReturn(Optional.of(course2));
        Mockito.when(courseRepository.findById(course3.getId()))
                .thenReturn(Optional.of(course3));
        Mockito.when(enrolmentService.findEnrolment(course1, student))
                .thenReturn(null);
        Mockito.when(enrolmentService.findEnrolment(course2, student))
                .thenReturn(enrolment);
        Mockito.when(userService.findStudentFromUser(userStudent))
                .thenReturn(student);
        Mockito.when(userService.findStudentFromUser(userNotStudent))
                .thenReturn(null);
        Mockito.when(userService.findStudentFromUser(userToBecomeStudent))
                .thenReturn(null)
                .thenReturn(createdStudent);
        Mockito.doNothing().when(userService).createStudentFromUser(userNotStudent);
        Mockito.when(courseRepository.search("Software Engineering for Service Computing"))
                .thenReturn(Arrays.asList(course1));
        Mockito.when(courseRepository.search("Service Computing"))
                .thenReturn(Arrays.asList(course1));
        Mockito.when(courseRepository.search("This module provides an in-depth"))
                .thenReturn(Arrays.asList(course1));
        Mockito.when(courseRepository.search("specifically"))
                .thenReturn(Arrays.asList(course1));
        Mockito.when(courseRepository.search("Engineering recent modular"))
                .thenReturn(Arrays.asList(course1));
    }

    @AfterEach
    public void tearDown() {
        courseList.forEach(course -> course = null);
        courseList = null;
        userStudent = null;
        userNotStudent = null;
        userToBecomeStudent = null;
        student = null;
        enrolment = null;
        courseRepository = null;
        userService = null;
        enrolmentService = null;
        courseService = null;
    }
}
