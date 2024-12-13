package com.horoscopo.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date; // Importación añadida para java.sql.Date
import com.horoscopo.procesaConexion.DatabaseConnector;

@WebServlet("/modificarUsuario")
public class modificarUsuarioServlets extends HttpServlet {
    private static final long serialVersionUID = 1L; // Añadir serialVersionUID

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");
        Date fechaNacimiento = null;
        if (fechaNacimientoStr != null && !fechaNacimientoStr.isEmpty()) {
            fechaNacimiento = Date.valueOf(fechaNacimientoStr);
        }
        String password = request.getParameter("password");

        // Construir la consulta de actualización dinámica
        StringBuilder sqlBuilder = new StringBuilder("UPDATE USUARIOS SET ");
        boolean firstField = true;

        if (nombre != null && !nombre.isEmpty()) {
            sqlBuilder.append("NOMBRE = ?");
            firstField = false;
        }
        if (username != null && !username.isEmpty()) {
            if (!firstField) sqlBuilder.append(", ");
            sqlBuilder.append("USERNAME = ?");
            firstField = false;
        }
        if (email != null && !email.isEmpty()) {
            if (!firstField) sqlBuilder.append(", ");
            sqlBuilder.append("EMAIL = ?");
            firstField = false;
        }
        if (fechaNacimiento != null) {
            if (!firstField) sqlBuilder.append(", ");
            sqlBuilder.append("FECHA_NACIMIENTO = ?");
            firstField = false;
        }
        if (password != null && !password.isEmpty()) {
            if (!firstField) sqlBuilder.append(", ");
            sqlBuilder.append("PASSWORD = ?");
        }

        sqlBuilder.append(" WHERE ID = ?");
        String sql = sqlBuilder.toString();

        try (Connection connection = DatabaseConnector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                int paramIndex = 1;

                if (nombre != null && !nombre.isEmpty()) {
                    preparedStatement.setString(paramIndex++, nombre);
                }
                if (username != null && !username.isEmpty()) {
                    preparedStatement.setString(paramIndex++, username);
                }
                if (email != null && !email.isEmpty()) {
                    preparedStatement.setString(paramIndex++, email);
                }
                if (fechaNacimiento != null) {
                    preparedStatement.setDate(paramIndex++, fechaNacimiento);
                }
                if (password != null && !password.isEmpty()) {
                    preparedStatement.setString(paramIndex++, password);
                }

                preparedStatement.setInt(paramIndex, id);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    request.setAttribute("message", "Usuario modificado exitosamente.");
                } else {
                    request.setAttribute("message", "No se encontró el usuario con el ID proporcionado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error al modificar el usuario.");
        }

        request.getRequestDispatcher("jsp/modificarUsuario.jsp").forward(request, response);
    }
}
