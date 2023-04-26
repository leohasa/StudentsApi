package com.ipc2.studentsapi.controller;

import com.ipc2.studentsapi.model.Student;
import com.ipc2.studentsapi.service.StudentService;
import com.ipc2.studentsapi.utils.GsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final StudentService studentService;
    private final GsonUtils<Student> gsonStudent;

    public LoginController() {
        studentService = new StudentService();
        gsonStudent = new GsonUtils<>();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var student = gsonStudent.readFromJson(req, Student.class);

        var studentFromDB = studentService.read(student.getId());

        if(studentFromDB == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if(studentFromDB.getPassword().equals(student.getPassword())){
            resp.setStatus(HttpServletResponse.SC_OK);
            gsonStudent.sendAsJson(resp, studentFromDB);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
