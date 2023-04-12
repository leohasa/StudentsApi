package com.ipc2.studentsapi.data;

import com.ipc2.studentsapi.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDB {
    private List<Student> students;

    public StudentDB(){
        students = new ArrayList<>();
        students.add(Student.builder().id(1).name("John").email("john@email.com").age(20).registered(true).build());
        students.add(Student.builder().id(2).name("Mary").email("mary@email.com").age(21).registered(false).build());
        students.add(Student.builder().id(3).name("Peter").email("peter@email.com").age(22).registered(true).build());
        students.add(Student.builder().id(4).name("Jane").email("jane@email.com").age(23).registered(false).build());
    }

    public void create (Student student) {
        student.setId(students.size() + 1);
        students.add(student);
    }

    public List<Student> readAll() {
        return students;
    }

    public Optional<Student> read(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }

    public void update(Student student) {
        var studentDB = students.stream().filter(s -> s.getId() == student.getId()).findFirst().orElse(null);

        if (studentDB != null) {
            studentDB.setName(student.getName());
            studentDB.setEmail(student.getEmail());
            studentDB.setAge(student.getAge());
            studentDB.setRegistered(student.isRegistered());
        }
    }

    public void delete(int id) {
        students.stream().filter(s -> s.getId() == id).findFirst().ifPresent(studentDB -> students.remove(studentDB));
    }
}
