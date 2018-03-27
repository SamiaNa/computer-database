package com.excilys.java.formation.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;

/**
 * Servlet implementation class EditComptuer
 */
@WebServlet("/EditComputer")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        RequestDispatcher rd = request.getRequestDispatcher("/static/views/editComputer.jsp");
        try {
            long id = Long.parseLong(idStr);
            Optional<Computer> optComp = ComputerService.INSTANCE.getComputerById(id);
            if (optComp.isPresent()) {
                request.setAttribute("computer", optComp.get());
            }else {

            }
            rd.forward(request, response);
        }catch(ServiceException | NumberFormatException e) {

        }

    }

}
