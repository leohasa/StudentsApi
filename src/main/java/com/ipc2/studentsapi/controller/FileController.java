package com.ipc2.studentsapi.controller;

import com.ipc2.studentsapi.model.Student;
import com.ipc2.studentsapi.utils.GsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet("/files")
@MultipartConfig
public class FileController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = req.getPart("archivoEntrada");
        String inputFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        guardarArchivo(filePart, inputFileName);

        try {
            String content = Files.readString(Paths.get("/tmp/" + inputFileName));
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");

        var out = response.getWriter();

        out.print("{\"message\": \"Archivo guardado\"}");
    }

    private void guardarArchivo(Part filePart, String nombreArchivo) {
        File ruta = new File("/tmp");
        File file = new File(ruta, nombreArchivo);

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo " + nombreArchivo + " guardado");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
