package com.excilys.java.formation.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.page.Page;

public class PaginationTag extends SimpleTagSupport{

    private Page page;
    private String target;
    private String search;
    private static Logger logger = LoggerFactory.getLogger(PaginationTag.class);

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getHref(int pageNumber, int pageSize) {
        return "\"" + this.target + "?pageNumber=" + pageNumber + "&search=" + this.search +"&pageSize="+ pageSize +"\"";
    }
    public void getPrev (JspWriter out) throws IOException {
        if (page.getNumber() > 1) {
            out.write(new StringBuilder()
                    .append("<li><a href=")
                    .append(getHref(page.getNumber() - 1, page.getSize()))
                    .append("aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span>\n </a></li>")
                    .toString());
        }
    }

    public void getPages(JspWriter out) throws IOException {
        for (int i = 0; i < 4 ; i++) {
            if (((page.getNumber() + i  <= page.getCount() / page.getSize()) && page.getCount() % page.getSize() == 0)
                    || (page.getNumber() + i <= (page.getCount() /page.getSize()) + 1 && page.getCount() % page.getSize() != 0)){
                out.write(new StringBuilder()
                        .append("<li><a href=")
                        .append(getHref(page.getNumber() + i, page.getSize())+">")
                        .append((i + page.getNumber())+" </a></li>")
                        .toString());
            }
        }
    }

    public void getNext(JspWriter out) throws IOException {
        if (((page.getNumber() + 1  <= page.getCount() / page.getSize()) && page.getCount() % page.getSize() == 0)
                || (page.getNumber() + 1 <= (page.getCount() /page.getSize()) + 1 && page.getCount() % page.getSize() != 0)){
            out.write(new StringBuilder()
                    .append("<li><a href=")
                    .append(getHref(page.getNumber() + 1, page.getSize()))
                    .append("aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span>\n </a></li>")
                    .toString());
        }
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        getPrev(out);
        getPages(out);
        getNext(out);
    }

}
