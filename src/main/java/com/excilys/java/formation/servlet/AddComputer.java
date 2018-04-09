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
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.CompanyDTOMapper;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

/**
 * Servlet implementation class AddCompanyServlet
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(AddComputer.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
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
            List<CompanyDTO> companyList = CompanyDTOMapper.INSTANCE.toDTOList(CompanyService.INSTANCE.getCompanyList());
            request.setAttribute("companyList", companyList);
        } catch (ServiceException e) {
            logger.error("Exception in addComputerServlet", e);
        }
        String submit = request.getParameter("submit");
        if (submit != null) {
            try {
                String name = request.getParameter("name");
                String introducedStr = request.getParameter("introduced");
                String discontinuedStr = request.getParameter("discontinued");
                String companyIdStr = request.getParameter("companyId");
                CompanyDTO companyDTO = new CompanyDTO();
                logger.info("Add computer servlet");
                try {
                    long id = Long.parseUnsignedLong(companyIdStr);
                    logger.info("Add computer valeur id : {}", id);
                    if (id != 0) {
                        companyDTO.setId(id);
                    }else {
                        companyDTO = null;
                    }
                }catch (NumberFormatException e) {

                    logger.info("companydto null");
                    companyDTO = null;
                }
                Builder computerDTOBuilder = new Builder();
                computerDTOBuilder.withName(name)
                .withIntroduced(introducedStr)
                .withDiscontinued(discontinuedStr)
                .withCompany(companyDTO);
                Optional<Computer> optComp = ComputerService.INSTANCE
                        .createComputer(ComputerDTOMapper.INSTANCE.toComputer(computerDTOBuilder.build()));
                if (optComp.isPresent()) {
                    request.setAttribute("res", "Computer added ");
                } else {
                    request.setAttribute("res", "Error");
                }

            } catch (ValidatorException e) {
                request.setAttribute("res", e.getMessage());
            } catch (ServiceException e) {
                logger.error("Exception in doPost AddCompanyServlet", e);
                throw new ServletException(e);
            }
        }
        rd.forward(request, response);
    }
}