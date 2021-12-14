package uk.ac.leedsbeckett.student.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
public class Enrolment {
    private @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JoinColumn(name="student_fk",referencedColumnName="id")
    @ToString.Exclude
    private Student student;
    @OneToOne
    @JoinColumn(name="course_fk",referencedColumnName="id")
    @ToString.Exclude
    private Course course;

    public Enrolment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Enrolment() {

    }
}
