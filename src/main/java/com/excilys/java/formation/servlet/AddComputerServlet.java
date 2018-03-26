package com.excilys.java.formation.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.dto.ComputerDTO.Builder;
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.persistence.ConnectionException;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.validator.ValidatorException;

/**
 * Servlet implementation class AddCompanyServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/static/views/addComputer.jsp");

        try {
            List<Company> companyList = CompanyService.INSTANCE.getCompanyList();
            request.setAttribute("companyList", companyList);
        } catch (DAOException | ConnectionException e) {
            logger.error("Exception in addComputerServlet", e);
        }

        String submit = request.getParameter("submit");
        if (submit != null) {
            try {
                String name = request.getParameter("name");
                String introducedStr = request.getParameter("introduced");
                String discontinuedStr = request.getParameter("discontinued");
                String companyIdStr = request.getParameter("companyId");
                Builder computerDTOBuilder = new Builder();
                computerDTOBuilder.setName(name)
                .setIntroduced(introducedStr)
                .setDiscontinued(discontinuedStr)
                .setCompanyId(companyIdStr);
                Optional<Computer> optComp = ComputerService.INSTANCE.createComputer(ComputerDTOMapper.INSTANCE.toComputer(computerDTOBuilder.build()));
                if (optComp.isPresent()) {
                    request.setAttribute("res", "Computer added ");
                } else {
                    request.setAttribute("res", "Error");
                }

            } catch (ValidatorException e) {
                request.setAttribute("res", e.getMessage());
            } catch (ConnectionException | DAOException e) {
                logger.error("Exception in doPost AddCompanyServlet", e);
                throw new ServletException(e);
            }
        }
        rd.forward(request, response);
    }
}
