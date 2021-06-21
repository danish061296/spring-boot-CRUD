package com.danish.springbootdemo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // get all users from the database
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // add student info to database with a new email
    // error, if email already exist
    public void addNewStudent(Student student) {
        Optional<Student> studentEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentEmail.isPresent()) {
            throw new IllegalStateException("Email is already taken.");
        }
        studentRepository.save(student);
    }

    // delete student from database by id
    public void deleteStudent(Long studentId) {
        boolean studentExist = studentRepository.existsById(studentId);
        if (!studentExist) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist.");
        }
        studentRepository.deleteById(studentId);

    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist."));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentEmail = studentRepository.findStudentByEmail(email);
            if (studentEmail.isPresent()) {
                throw new IllegalStateException("Error:This email is currently in use for this account.");
            }
            student.setEmail(email);


        }
    }
}
