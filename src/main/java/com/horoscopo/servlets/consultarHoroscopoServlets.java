package com.horoscopo.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/horoscopo")
public class consultarHoroscopoServlets extends HttpServlet {
    private static final long serialVersionUID = 1L; // Añadir serialVersionUID

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int year = Integer.parseInt(request.getParameter("año"));
        int remainder = year % 12;
        String signo = getSignoFromRemainder(remainder);

        request.setAttribute("signo", signo);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }

    private String getSignoFromRemainder(int remainder) {
        switch (remainder) {
            case 0: return "Mono";
            case 1: return "Gallo";
            case 2: return "Perro";
            case 3: return "Cerdo";
            case 4: return "Rata";
            case 5: return "Buey";
            case 6: return "Tigre";
            case 7: return "Conejo";
            case 8: return "Dragón";
            case 9: return "Serpiente";
            case 10: return "Caballo";
            case 11: return "Cabra";
            default: return "Desconocido";
        }
    }
}
