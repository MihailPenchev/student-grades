package com.ltp.gradesubmission.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @NonNull
    @NotBlank(message = "Subject cannot be blank")
    @Column(name="subject", nullable = false)
    private String subject;

    @NonNull
    @NotBlank(message = "Course code cannot be blank")
    @Column(name="code", nullable = false, unique = true)
    private String code;

    @NonNull
    @NotBlank(message = "Description cannot be blank")
    @Column(name="description", nullable = false)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private List<Grade> grades;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="course_student",
            joinColumns = { @JoinColumn(name="course_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name="student_id", referencedColumnName="id") }
    )
    private Set<Student> students;
}
