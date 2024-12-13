package com.horoscopo.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.horoscopo.modelo.Usuario;
import com.horoscopo.procesaConexion.DatabaseConnector;

@WebServlet("/listarUsuarios")
public class buscarUsuarioServlets extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT ID, NOMBRE, USERNAME, EMAIL, FECHA_NACIMIENTO, ANIMAL FROM USUARIOS";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(resultSet.getInt("ID"));
                    usuario.setNombre(resultSet.getString("NOMBRE"));
                    usuario.setUsername(resultSet.getString("USERNAME"));
                    usuario.setEmail(resultSet.getString("EMAIL"));
                    usuario.setFechaNacimiento(resultSet.getDate("FECHA_NACIMIENTO"));
                    usuario.setAnimal(resultSet.getString("ANIMAL"));
                    listaUsuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("listaUsuarios", listaUsuarios);
        request.getRequestDispatcher("jsp/listarUsuarios.jsp").forward(request, response);
    }
}
