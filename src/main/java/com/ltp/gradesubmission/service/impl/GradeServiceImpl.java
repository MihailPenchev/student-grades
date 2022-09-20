package com.ltp.gradesubmission.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.StudentNotEnrolledException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.service.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {
    private GradeRepository gradeRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    @Override
    public Grade getGrade(Long studentId, Long courseId) {
        return getGrade(studentId, courseId, "");
    }

    @Override
    public Grade saveGrade(Grade grade, Long studentId, Long courseId) {
        Student student = getStudent(studentId);
        Course course = getCourse(courseId);
        if (!course.getStudents().contains(student)) {
            throw new StudentNotEnrolledException("The student with id=" + studentId
                    + " is not enrolled in the course with id=" + courseId);
        }
        grade.setStudent(student);
        grade.setCourse(course);
        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(String score, Long studentId, Long courseId) {
        Grade grade = getGrade(studentId, courseId, " to update.");
        grade.setScore(score);
        return gradeRepository.save(grade);
    }

    @Override
    public void deleteGrade(Long studentId, Long courseId) {
        gradeRepository.delete(getGrade(studentId, courseId," to delete."));
    }

    @Override
    public List<Grade> getStudentGrades(Long studentId) {
        return new ArrayList<>(getStudent(studentId).getGrades());
    }

    @Override
    public List<Grade> getCourseGrades(Long courseId) {
        return new ArrayList<>(getCourse(courseId).getGrades());
    }

    @Override
    public List<Grade> getAllGrades() {
        return StreamSupport.stream(gradeRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    private Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Could not find Course with id=" + id));
    }

    private Student getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Could not find Student with id=" + id));
    }
    private Grade getGrade(Long studentId, Long courseId, String errMsgSuffix) {
        return gradeRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(() -> new EntityNotFoundException(
                "Could not find grade for student with id=" + studentId + " and course with id=" + courseId + errMsgSuffix));
    }

}
