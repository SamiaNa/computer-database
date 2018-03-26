package com.excilys.java.formation.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.page.ComputerDTOPage;
import com.excilys.java.formation.persistence.ConnectionException;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.validator.ValidatorException;

/**
 * Servlet implementation class ComputerListServlet
 */
@WebServlet("/ComputerListServlet")
public class ComputerListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ComputerListServlet.class);

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
            RequestDispatcher rd = request.getRequestDispatcher("/static/views/dashboard.jsp");
            try {
                pageNumber = Integer.parseUnsignedInt(pageNumberStr);
                pageSize = Integer.parseUnsignedInt(pageSizeStr);
                logger.info("Page number = "+pageNumber+", page size = "+pageSize);
            } catch (NumberFormatException e) {
                logger.error("Failed to parse "+pageNumberStr+" or "+pageSizeStr+" as an unsigned int");
                throw new ServletException(e);
            }
            ComputerDTOPage computerPage = (ComputerDTOPage) request.getAttribute("computerPage");
            if (computerPage == null) {
                computerPage = new ComputerDTOPage();
            }

            computerPage.getPage(pageNumber, pageSize);
            logger.info("Successfully fetched page content (page number="+pageNumber+" page size="+pageSize+")");
            request.setAttribute("computerPage", computerPage);
            rd.forward(request, response);
        } catch (ConnectionException | DAOException | ValidatorException e) {
            logger.error("Exception in ComputerListServlet", e);
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
