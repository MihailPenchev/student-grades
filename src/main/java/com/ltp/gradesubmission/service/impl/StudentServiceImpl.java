package com.ltp.gradesubmission.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    @Override
    public Student getStudent(Long id) {
        return getStudent(id, "");
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.delete(getStudent(id, " to delete."));
    }

    @Override
    public List<Student> getStudents() {
        return StreamSupport
                .stream(studentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Course> getEnrolledCourses(Long studentId) {
        return new ArrayList<>(Optional.ofNullable(getStudent(studentId).getCourses()).orElseGet(HashSet::new));
    }

    private Student getStudent(Long id, String errMsgPrefix) {
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Could not find Student with id=" + id + errMsgPrefix));
    }

}