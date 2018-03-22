package com.excilys.java.formation.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.page.ComputerPage;
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

        ComputerPage computerPage = ComputerPage.getPage();
        ComputerService computerService = ComputerService.INSTANCE;
        try {

            RequestDispatcher rd = request.getRequestDispatcher(request.getContextPath()+"dashboard.jsp");
            request.setAttribute("computerCount", computerService.count());
            String pageNumberStr = request.getParameter("pageNumber");
            String pageSizeStr = request.getParameter("pageSize");
            int pageNumber = 0;
            int pageSize = 0;
            try {
                pageNumber = Integer.parseUnsignedInt(pageNumberStr);
                pageSize = Integer.parseUnsignedInt(pageSizeStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "");
                rd.forward(request, response);
                return;
                // Log
            }

            List<ComputerDTO> computers = ComputerDTOMapper.INSTANCE
                    .toDTOList(computerPage.getPage(pageNumber, pageSize));
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
