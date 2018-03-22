package com.excilys.java.formation.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.java.formation.page.ComputerDTOPage;
import com.excilys.java.formation.persistence.DAOException;

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
        try {
            String pageNumberStr = request.getParameter("pageNumber");
            String pageSizeStr = request.getParameter("pageSize");
            int pageNumber = 0;
            int pageSize = 0;
            RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
            try {
                pageNumber = Integer.parseUnsignedInt(pageNumberStr);
                pageSize = Integer.parseUnsignedInt(pageSizeStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "");
                rd.forward(request, response);
                return;
            }
            ComputerDTOPage computerPage = (ComputerDTOPage) request.getAttribute("computerPage");
            if (computerPage == null) {
                computerPage = new ComputerDTOPage();
            }
            computerPage.getPage(pageNumber, pageSize);
            request.setAttribute("computerPage", computerPage);
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
