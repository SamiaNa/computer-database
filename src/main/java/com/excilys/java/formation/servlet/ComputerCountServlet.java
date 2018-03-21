package com.excilys.java.formation.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.ComputerService;

/**
 * Servlet implementation class ComputerCountServlet
 */
@WebServlet("/ComputerCountServlet")
public class ComputerCountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerCountServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ComputerService computerService = ComputerService.INSTANCE;
        int count;
        try {
            count = computerService.count();
            RequestDispatcher rd = request.getRequestDispatcher("/dashboard.jsp");
            request.setAttribute("computerCount", count);
            rd.forward(request, response);
        } catch (ClassNotFoundException | DAOException e) {
            throw new ServletException(e);
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
