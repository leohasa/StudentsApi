package com.ipc2.studentsapi.controller;

import com.ipc2.studentsapi.data.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/report")
public class StudentsReport extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resources = request.getServletContext().getRealPath("/resources/");

        try (InputStream inputStream = new FileInputStream(resources + "StudentsReport1.jasper");){
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "attachment; filename=students.pdf");

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);

            Map<String, Object> params = new HashMap<>();
            params.put("userName", "leonidas");

            // Llenar el reporte con los datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, Conexion.obtenerConexion());

            // Exportar el reporte a PDF y escribirlo en la respuesta HTTP
            OutputStream out = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            out.flush();
            out.close();
        } catch (IOException | JRException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException(e);
        }
    }


}
