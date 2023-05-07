package com.ipc2.studentsapi.data;

import com.ipc2.studentsapi.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDB {
    private Connection conexion = Conexion.obtenerConexion();

    public StudentDB(){
    }

    public void create (Student student) {
        String query = "INSERT INTO students (id, name, email, password, age, registered) VALUES (?, ?, ?, ?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getPassword());
            preparedStatement.setInt(5, student.getAge());
            preparedStatement.setBoolean(6, student.isRegistered());
            preparedStatement.executeUpdate();
            System.out.println("Estudiante creado");
        } catch (SQLException e) {
            System.out.println("Error al crear estudiante: " + e);
        }
    }

    public List<Student> readAll() {
        var students = new ArrayList<Student>();
        try (var stmt = conexion.createStatement();
             var resultSet = stmt.executeQuery("SELECT *  FROM students")) {

            while (resultSet.next()) {

                var id = resultSet.getInt("id");
                var nombre = resultSet.getString("name");
                var password = resultSet.getString("password");
                var email = resultSet.getString("email");
                var age = resultSet.getInt("age");
                var registered = resultSet.getBoolean("registered");

                var student = new Student(id, nombre, email, password, age, registered);
                students.add(student);
            }
        }catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return students;
    }

    public Optional<Student> read(int id) {
        String query = "SELECT * FROM students WHERE id = ?";
        Student student = null;

        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    var nombre = resultSet.getString("name");
                    var password = resultSet.getString("password");
                    var email = resultSet.getString("email");
                    var age = resultSet.getInt("age");
                    var registered = resultSet.getBoolean("registered");

                    student = new Student(id, nombre, email, password, age, registered);
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }

        return Optional.ofNullable(student);
    }

    public void update(Student student) {
        String query = "UPDATE students SET name = ?, email = ?, password = ?, age = ?, registered = ? WHERE id = ?";

        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setString(3, student.getPassword());
            preparedStatement.setInt(4, student.getAge());
            preparedStatement.setBoolean(5, student.isRegistered());
            preparedStatement.setInt(6, student.getId());
            preparedStatement.executeUpdate();
            System.out.println("Estudiante actualizado");
        } catch (SQLException e) {
            System.out.println("Error al actualizar estudiante: " + e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM students WHERE id = ?";

        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Estudiante eliminado");
        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante: " + e);
        }
    }
}
