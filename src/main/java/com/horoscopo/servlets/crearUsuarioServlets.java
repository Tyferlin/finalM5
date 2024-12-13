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
import java.sql.Date;
import com.horoscopo.procesaConexion.DatabaseConnector;

@WebServlet("/register")
public class crearUsuarioServlets extends HttpServlet {
    private static final long serialVersionUID = 1L; 

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");
        Date fechaNacimiento = Date.valueOf(fechaNacimientoStr);
        String password = request.getParameter("password");

        // Guarda el usuario en la base de datos
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO USUARIOS (NOMBRE, USERNAME, EMAIL, FECHA_NACIMIENTO, PASSWORD) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, email);
                preparedStatement.setDate(4, fechaNacimiento);
                preparedStatement.setString(5, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("login.jsp");
    }
}


