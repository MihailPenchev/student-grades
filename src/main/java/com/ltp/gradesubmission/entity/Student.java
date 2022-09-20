package com.ltp.gradesubmission.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @NonNull
    @NotBlank(message = "Name cannot be blank")
    @Column(name="name", nullable = false)
    private String name;

    @NonNull
    @Past(message = "The birth date must be in the past")
    @Column(name="birth_date", nullable = false)
    private LocalDate birthDate;

    @JsonIgnore
    @OneToMany(mappedBy="student", cascade = CascadeType.ALL)
    private Set<Grade> grades;

    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;
}
