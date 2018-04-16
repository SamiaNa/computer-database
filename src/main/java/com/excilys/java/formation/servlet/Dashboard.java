package com.excilys.java.formation.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.java.formation.page.ComputerDTOPage;
import com.excilys.java.formation.page.ComputerPage;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

/**
 * Servlet implementation class ComputerListServlet
 */

@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(Dashboard.class);
    private static final String SEARCH = "search";
    private static final String ORDER = "order";
    private static final String BY = "by";

    @Autowired
    private ComputerService computerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            RequestDispatcher rd = request.getRequestDispatcher("/static/views/dashboard.jsp");
            int pageNumber = getUnsignedIntFromParam(request, response, "pageNumber", 1);
            int pageSize = getUnsignedIntFromParam(request, response, "pageSize", 10);
            ComputerDTOPage computerPage = new ComputerDTOPage(computerService);
            String search = request.getParameter(SEARCH);
            String by = request.getParameter(BY);
            String order = request.getParameter(ORDER);
            computerPage.getPage(by, order, search, pageNumber, pageSize);
            setAttributes(request, by, order, search, computerPage);
            logger.info("Successfully fetched page content (page number= {}, page size={})", pageNumber, pageSize);
            rd.forward(request, response);
        } catch (ServiceException e) {
            logger.error("Exception in ComputerListServlet", e);
            response.sendRedirect("static/views/500.jsp");
        } catch (ValidatorException e) {
            logger.error("Validator exception in doGet ", e);
            response.sendRedirect("static/views/404.jsp");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String checked = request.getParameter("selection");
        if (checked != null) {
            String[] computerIds = checked.split(",");
            List<Long> ids = new ArrayList<>();
            for (String strId : computerIds) {
                ids.add(Long.parseLong(strId));
            }
            computerService.deleteComputer(ids);
        }
        doGet(request, response);
    }

    private int getUnsignedIntFromParam(HttpServletRequest request, HttpServletResponse response, String name,
            int defaultValue) throws IOException {
        String numberStr = request.getParameter(name);
        int number = defaultValue;
        if (numberStr != null) {
            try {
                number = Integer.parseUnsignedInt(numberStr);
                logger.info("{} = {}", name, number);
            } catch (NumberFormatException e) {
                logger.error("Failed to parse {} as unsigned int", numberStr);
                response.sendRedirect("static/views/404.jsp");
                return -1;
            }
        }
        return number;
    }


    private void setAttributes(HttpServletRequest request, String by, String order, String search, ComputerPage page) {
        request.setAttribute(ORDER, order);
        request.setAttribute(SEARCH, search);
        request.setAttribute(BY, by);
        request.setAttribute("page", page);
    }

}