package com.ipc2.studentsapi.service;

import com.ipc2.studentsapi.data.StudentDB;
import com.ipc2.studentsapi.model.Student;

import java.util.List;

public class StudentService {
    private final StudentDB studentDB;

    public StudentService() {
        studentDB = new StudentDB();
    }

    public void create(Student student) {
        studentDB.create(student);
    }

    public List<Student> readAll() {
        return studentDB.readAll();
    }

    public Student read(int id) {
        return studentDB.read(id).orElse(null);
    }

    public void update(Student student) {
        studentDB.update(student);
    }

    public void delete(int id) {
        studentDB.delete(id);
    }
}
