package com.excilys.java.formation.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.CompanyService;

/**
 * Servlet implementation class CompanyListServlet
 */
@WebServlet("/CompanyListServlet")
public class CompanyListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Company> listCompanies = CompanyService.INSTANCE.getCompanyList();
            List<Integer> list = new ArrayList<Integer>();
            list.add(2);
            list.add(3);
            list.add(4);
            request.setAttribute("companyList", list);
            System.out.println(list);
            RequestDispatcher rd = request.getRequestDispatcher("/static/views/addComputer.jsp");
            rd.forward(request, response);
        } catch (ClassNotFoundException | DAOException e) {
            throw new ServletException(e);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
