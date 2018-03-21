package com.excilys.java.formation.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.ComputerService;

/**
 * Servlet implementation class ComputerListServlet
 */
@WebServlet("/ComputerListServlet")
public class ComputerListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerListServlet() {
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
        try {

            RequestDispatcher rd = request.getRequestDispatcher("/dashboard.jsp");
            request.setAttribute("computerCount", computerService.count());
            String offsetStr = request.getParameter("offset");
            String limitStr =  request.getParameter("limit");
            int offset = 0;
            int limit = 0;
            try {
                offset = Integer.parseUnsignedInt(offsetStr);
                limit = Integer.parseUnsignedInt(limitStr);
            }catch(NumberFormatException e) {
                request.setAttribute("errorMessage", "");
                rd.forward(request, response);
                return;
                //Log
            }
            List<Computer> computers = computerService.getComputerList(offset, limit);
            request.setAttribute("computers", computers);
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
