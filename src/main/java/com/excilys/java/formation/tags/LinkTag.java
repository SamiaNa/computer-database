package com.excilys.java.formation.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.page.Page;

public class LinkTag extends SimpleTagSupport {

    private String target;
    private String search;
    private Page page;
    private String by;
    private String order;
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

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        String href = "\"" + this.target + "?pageNumber=" + this.page.getNumber() + "&by=" + this.by + "&order="
                + this.order + "&search=" + this.search + "&pageSize=" + this.page.getSize() + "\"";
        logger.info(href);
        out.write(href);
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}