package com.excilys.java.formation.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.page.Page;

public class OrderByTag extends SimpleTagSupport {

    private String target;
    private String search;
    private Page page;
    private String by;
    private static Logger logger = LoggerFactory.getLogger(LinkTag.class);

    public void setTarget(String target) {
        this.target = target;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        String href = "<a href=\"" + this.target + "?pageNumber=" + this.page.getNumber() + "&by=" + this.by
                + "&search=" + this.search;
        String ascending = href + "&order=ASC&pageSize=" + this.page.getSize()
        + "\"> <i class=\"fa fa-angle-up fa-lg\"></i></a>";
        String descending = href + "&order=DESC&pageSize=" + this.page.getSize()
        + "\"> <i class=\"fa fa-angle-down fa-lg\"></i></a>";
        logger.info("{}, {}", ascending, descending);
        out.write(ascending + descending);
    }

}