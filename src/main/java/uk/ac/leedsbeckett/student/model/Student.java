package uk.ac.leedsbeckett.student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Student {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    private String studentId;
    private String surname;
    private String forename;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Enrolment> enrolmentList = new ArrayList<>();

    public Student() {
    }

    public Student(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
        populateStudentId();
    }

    public void populateStudentId() {
        if (this.studentId == null) {
            this.studentId = "c" +
                    RandomStringUtils.random(1, '7', '3') +
                    RandomStringUtils.randomNumeric(6);
        }
    }
}
