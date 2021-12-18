package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.student.model.Course;
import uk.ac.leedsbeckett.student.model.Enrolment;
import uk.ac.leedsbeckett.student.model.EnrolmentRepository;
import uk.ac.leedsbeckett.student.model.Student;

@Component
public class EnrolmentService {

    private final EnrolmentRepository enrolmentRepository;

    public EnrolmentService(EnrolmentRepository enrolmentRepository) {
        this.enrolmentRepository = enrolmentRepository;
    }

    public Enrolment createEnrolment(Student student, Course course) {
        Enrolment enrolment = new Enrolment(student, course);
        return enrolmentRepository.save(enrolment);
    }

    public Enrolment findEnrolment(Course course, Student student) {
        return enrolmentRepository.findEnrolmentByCourseAndStudent(course, student);
    }
}
