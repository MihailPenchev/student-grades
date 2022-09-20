package com.ltp.gradesubmission.service.impl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    @Override
    public Course getCourse(Long id) {
        return getCourse(id, "");
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.delete(getCourse(id, " to delete."));
    }

    @Override
    public List<Course> getCourses() {
        return StreamSupport
                .stream(courseRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Course addStudentToCourse(Long courseId, Long studentId) {
        Course course =  getCourse(courseId);
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException(
                "Could not find student with id=" + studentId));
        if (course.getStudents() == null) {
            course.setStudents(new HashSet<>());
        }
        course.getStudents().add(student);

        return courseRepository.save(course);
    }

    @Override
    public List<Student> getEnrolledStudents(Long courseId) {
        return new ArrayList<>(Optional.ofNullable(getCourse(courseId).getStudents()).orElseGet(HashSet::new));
    }

    private Course getCourse(Long id, String errMsgSuffix) {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Could not find Course with id=" + id + errMsgSuffix));
    }

}
