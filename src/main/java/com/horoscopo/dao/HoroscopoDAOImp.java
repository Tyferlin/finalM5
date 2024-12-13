package com.horoscopo.dao;

import com.horoscopo.modelo.Horoscopo;
import com.horoscopo.procesaConexion.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoroscopoDAOImp implements HoroscopoDAO {
    private static final String SELECT_ALL_HOROSCOPO = "SELECT ANIMAL, FECHA_INICIO, FECHA_FIN FROM HOROSCOPO";

    @Override
    public List<Horoscopo> obtenerHoroscopo() {
        List<Horoscopo> listaHoroscopo = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_HOROSCOPO)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Horoscopo horoscopo = new Horoscopo();
                horoscopo.setAnimal(rs.getString("ANIMAL"));
                horoscopo.setFechaInicio(rs.getDate("FECHA_INICIO"));
                horoscopo.setFechaFin(rs.getDate("FECHA_FIN"));
                listaHoroscopo.add(horoscopo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaHoroscopo;
    }
}

