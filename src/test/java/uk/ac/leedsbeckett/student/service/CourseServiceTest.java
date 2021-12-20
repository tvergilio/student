package uk.ac.leedsbeckett.student.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CourseServiceTest {

    @TestConfiguration
    class CourseServiceImplTestContextConfiguration {
        @Bean
        public CourseService courseService() {
            return new CourseService(courseRepository, userService, enrolmentService);
        }
    }

    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private EnrolmentService enrolmentService;
    @Autowired
    private CourseService courseService;

    private Course course1, course2, course3, course4, course5;
    private List<Course> courseList;
    private User userStudent;
    private User userNotStudent;
    private Student student;
    private Enrolment enrolment;

    @BeforeEach
    public void setUp() {
        createTestObjects();
        defineMockingBehaviour();
    }

    @Test
    void testGetCourses_returnsExistingCourses() {
        ModelAndView result = courseService.getCourses();
        assertEquals(result.getViewName(), "courses");
        assertNotNull(result.getModel());
        assertEquals(1, result.getModel().size());
        Object coursesReturned = result.getModel().get("courses");
        if (coursesReturned instanceof List<?>) {
            assertTrue(((List<?>) coursesReturned).containsAll(courseList));
        }
    }

    @Test
    void testGetCourseWithoutEnrolment_userIsStudent_returnsExistingCourse() {
        ModelAndView result = courseService.getCourse(course1.getId(), userStudent);
        assertEquals(result.getViewName(), "course");
        assertNotNull(result.getModel());
        assertEquals(3, result.getModel().size());
        Object courseReturned = result.getModel().get("course");
        if (courseReturned instanceof Course) {
            assertEquals(course1, (Course) courseReturned);
        }
        Object studentReturned = result.getModel().get("student");
        if (studentReturned instanceof Student) {
            assertEquals(student, studentReturned);
        }
        assertFalse((Boolean) result.getModel().get("isEnrolled"));
    }

    private void createTestObjects() {
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

        userStudent = new User(faker.name().username(), Role.STUDENT, "user@gmail.com", faker.twinPeaks().character());
        student = new Student(faker.name().firstName(), faker.name().lastName());
        userStudent.setStudent(student);

        enrolment = new Enrolment(student, course2);
    }

    private void defineMockingBehaviour() {
        courseList = Arrays.asList(course1, course2, course3, course4, course5);

        Mockito.when(courseRepository.findAll())
                .thenReturn(courseList);
        Mockito.when(courseRepository.findById(course1.getId()))
                .thenReturn(Optional.of(course1));
        Mockito.when(enrolmentService.findEnrolment(course1, student))
                .thenReturn(null);
        Mockito.when(enrolmentService.findEnrolment(course2, student))
                .thenReturn(enrolment);
        Mockito.when(userService.findStudentFromUser(userStudent))
                .thenReturn(student);
        Mockito.when(userService.findStudentFromUser(userNotStudent))
                .thenReturn(null);
        Mockito.when(courseRepository.findById(course2.getId()))
                .thenReturn(Optional.of(course2));
        Mockito.when(courseRepository.search("Software Engineering for Service Computing"))
                .thenReturn(Arrays.asList(course1));
        Mockito.when(courseRepository.search("Service-Oriented"))
                .thenReturn(Arrays.asList(course1));
        Mockito.when(courseRepository.search("paradigm"))
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
        student = null;
        enrolment = null;
        courseRepository = null;
        userService = null;
        enrolmentService = null;
        courseService = null;
    }
}