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

import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.dto.ComputerDTO.Builder;
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

/**
 * Servlet implementation class EditComptuer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(EditComputer.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/static/views/editComputer.jsp");
        try {
            String idStr = request.getParameter("id");
            long id = Long.parseLong(idStr);
            Optional<Computer> optComp = ComputerService.INSTANCE.getComputerById(id);
            if (optComp.isPresent()) {
                List<Company> companyList = CompanyService.INSTANCE.getCompanyList();
                request.setAttribute("companyList", companyList);
                request.setAttribute("computer", ComputerDTOMapper.INSTANCE.toDTO(optComp.get()));
            }else {
                rd = request.getRequestDispatcher("/static/views/404.jsp");
                request.setAttribute("message", "No computer with id "+idStr);
            }
            rd.forward(request, response);
        }catch(ServiceException e) {
            rd = request.getRequestDispatcher("/static/views/500.jsp");
            request.setAttribute("stacktrace", e.getStackTrace());
            rd.forward(request, response);
        }catch(NumberFormatException e) {
            response.sendRedirect("static/views/404.jsp");
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
        RequestDispatcher rd = request.getRequestDispatcher("/static/views/editComputer.jsp");
        try {
            if (submit == null){
            }else {
                String idStr = request.getParameter("id");
                String name = request.getParameter("name");
                String introducedStr = request.getParameter("introduced");
                String discontinuedStr = request.getParameter("discontinued");
                String companyIdStr = request.getParameter("companyId");
                CompanyDTO companyDTO = new CompanyDTO();
                try {
                    long id = Long.parseUnsignedLong(companyIdStr);
                    if (id != 0) {
                        companyDTO.setId(id);
                    }else {
                        companyDTO = null;
                    }
                }catch (NumberFormatException e) {
                    companyDTO = null;
                }
                Builder computerDTOBuilder = new Builder();
                computerDTOBuilder.withId(idStr)
                .withName(name)
                .withIntroduced(introducedStr)
                .withDiscontinued(discontinuedStr)
                .withCompany(companyDTO);
                ComputerService.INSTANCE.updateComputer(ComputerDTOMapper.INSTANCE.toComputer(computerDTOBuilder.build()));

            }
            rd.forward(request, response);
        }catch(ServiceException | NumberFormatException | ValidatorException e) {
            rd = request.getRequestDispatcher("/static/views/404.jsp");
            logger.error("Exception in doPost ", e);
            rd.forward(request, response);
        }

    }

}