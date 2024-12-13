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
import com.horoscopo.procesaConexion.DatabaseConnector;

@WebServlet("/eliminarUsuario")
public class eliminarUsuarioServlets extends HttpServlet {
    private static final long serialVersionUID = 1L; // Añadir serialVersionUID

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        // Elimina el usuario de la base de datos
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "DELETE FROM USUARIOS WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    request.setAttribute("message", "Usuario eliminado exitosamente.");
                } else {
                    request.setAttribute("message", "No se encontró el usuario con el ID proporcionado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error al eliminar el usuario.");
        }

        request.getRequestDispatcher("jsp/eliminarUsuario.jsp").forward(request, response);
    }
}
