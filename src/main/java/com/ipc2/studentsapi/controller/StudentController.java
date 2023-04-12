package com.ipc2.studentsapi.controller;

import com.ipc2.studentsapi.model.Student;
import com.ipc2.studentsapi.service.StudentService;
import com.ipc2.studentsapi.utils.GsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/students/*")
public class StudentController extends HttpServlet {

    private final GsonUtils<Student> gsonStudent;

    private final StudentService studentService;

    public StudentController() {
        gsonStudent = new GsonUtils<>();
        studentService = new StudentService();
    }

    // GET students/
    // GET students/id
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){

            var students = studentService.readAll();

            gsonStudent.sendAsJson(response, students);
            return;
        }

        int studentId = processPath(request, response);

        response.setStatus(HttpServletResponse.SC_OK);
        gsonStudent.sendAsJson(response, studentService.read(studentId));
    }

    // Adds new student in DB
    // POST students/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){

            var student = gsonStudent.readFromJson(request, Student.class);

            studentService.create(student);

            response.setStatus(HttpServletResponse.SC_CREATED);
            gsonStudent.sendAsJson(response, student);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // Updates a student in DB
    // PUT students/1
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processPath(request, response);

        var student = gsonStudent.readFromJson(request, Student.class);

        studentService.update(student);

        response.setStatus(HttpServletResponse.SC_OK);
        gsonStudent.sendAsJson(response, student);
    }

    // Deletes a student in DB
    // DELETE students/id
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int studentId = processPath(request, response);

        Student student = studentService.read(studentId);

        studentService.delete(studentId);

        response.setStatus(HttpServletResponse.SC_OK);
        gsonStudent.sendAsJson(response, student);
    }

    private int processPath(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        String httpMethod = request.getMethod();

        if(httpMethod.equals("PUT") || httpMethod.equals("DELETE")) {
            if(pathInfo == null || pathInfo.equals("/")){

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return -1;
            }
        }

        String[] splits = pathInfo.split("/");
        if(splits.length != 2) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return -1;
        }

        String studentId = splits[1];

        try {
            Integer.parseInt(studentId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return -1;
        }

        if(studentService.read(Integer.parseInt(studentId)) == null) {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return -1;
        }

        return Integer.parseInt(studentId);
    }
}
