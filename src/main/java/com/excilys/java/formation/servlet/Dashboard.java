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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.page.ComputerDTOPage;
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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
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
            if (pageNumberStr == null || pageSizeStr == null) {
                pageNumber = 1;
                pageSize = 10;
            } else {
                try {
                    pageNumber = Integer.parseUnsignedInt(pageNumberStr);
                    pageSize = Integer.parseUnsignedInt(pageSizeStr);
                    logger.info("Page number = " + pageNumber + ", page size = " + pageSize);
                } catch (NumberFormatException e) {
                    logger.error("Failed to parse " + pageNumberStr + " or " + pageSizeStr + " as an unsigned int");
                    response.sendRedirect("static/views/404.jsp");
                    return;
                }
            }
            ComputerDTOPage computerPage = new ComputerDTOPage();
            String search = request.getParameter("search");
            String by = request.getParameter("by");
            String order = request.getParameter("order");
            if (StringUtils.isBlank(search)) {
                if (StringUtils.isBlank(order)) {
                    computerPage.getPage(pageNumber, pageSize);
                }else {
                    computerPage.getPageOrder(by, order, pageNumber, pageSize);
                    request.setAttribute("order", order);
                    request.setAttribute("search", search);
                    request.setAttribute("by", by);
                }
            }else {
                request.setAttribute("search", search);
                if (StringUtils.isBlank(order)) {
                    computerPage.getPage(search, pageNumber, pageSize);
                }else {
                    computerPage.getPageOrder(by, order, search, pageNumber, pageSize);
                    logger.info("page size : {}", computerPage.getDTOElements().size());;
                    request.setAttribute("order", order);
                    request.setAttribute("search", search);
                    request.setAttribute("by", by);
                }
            }
            logger.info(
                    "Successfully fetched page content (page number=" + pageNumber + " page size=" + pageSize + ")");
            request.setAttribute("page", computerPage);
            rd.forward(request, response);
        } catch (ServiceException e) {
            logger.error("Exception in ComputerListServlet", e);
            throw new ServletException(e);
        } catch (ValidatorException e) {
            logger.error("Validator exception in doGet ", e);
            response.sendRedirect("static/views/404.jsp");

        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String checked = request.getParameter("selection");
        if (checked != null) {
            String[] computerIds = checked.split(",");
            List <Long> ids = new ArrayList<>();
            for (String strId : computerIds) {
                ids.add(Long.parseLong(strId));
            }
            try {
                ComputerService.INSTANCE.deleteComputer(ids);
            } catch (ServiceException e) {
                logger.error("Exception in doPost", e);
                RequestDispatcher rd = request.getRequestDispatcher("/static/views/500.jsp");
                request.setAttribute("stacktrace", e.getStackTrace());
                rd.forward(request, response);
            }
        }
        doGet(request, response);
    }

}
