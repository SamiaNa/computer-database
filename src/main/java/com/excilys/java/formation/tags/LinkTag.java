package com.excilys.java.formation.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkTag extends SimpleTagSupport {

    private String target;
    private int pageNumber;
    private int pageSize;
    private static Logger logger = LoggerFactory.getLogger(LinkTag.class);


    public void setTarget(String target) {
        this.target = target;
    }

    public void setPageNumber(int page) {
        this.pageNumber = page;
    }

    public void setPageSize(int limit) {
        this.pageSize = limit;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        String href = "\""+this.target + "?pageNumber=" + this.pageNumber + "&pageSize="+ this.pageSize +"\"";
        logger.info(href);
        out.write(href);
    }
}
