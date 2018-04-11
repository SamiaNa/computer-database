package com.excilys.java.formation.page;

import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public abstract class Page {

    protected int offset;
    protected int size;
    protected int count;
    protected int number;
    protected String search;
    protected String orderCriteria;
    protected String order;

    protected static final int DEFAULT_SIZE = 10;

    public abstract void nextPage() throws ServiceException, ValidatorException;

    public abstract void prevPage() throws ServiceException, ValidatorException;

    public abstract void getPage() throws ValidatorException, ServiceException;

    public int getSize() {
        return size;
    }

    public int getNumber() {
        return number;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {
        return this.search;
    }

    public void setOrderCriteria(String orderCriteria) {
        this.orderCriteria = orderCriteria;
    }

    public String getOrderCriteria() {
        return this.orderCriteria;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int offsetNextPage(int dbSize) {
        if (offset + size <= dbSize) {
            offset += size;
        }
        return offset;
    }

    public int offsetPrevPage() {
        offset -= size;
        if (offset < 0) {
            offset = 0;
        }
        return offset;
    }

    public int getNumberOfPages() {
        return (this.count / this.size) + ((this.count % this.size) == 0 ? 0 : 1);
    }

    public int offsetGetPage(int number, int dbSize) {
        if (size > dbSize) {
            this.number = 1;
            this.offset = 0;
            return 0;
        }
        if ((number - 1) * size <= dbSize) {
            this.offset = ((number - 1) * size);
        } else {
            this.offset = dbSize - size;
        }
        return this.offset;
    }

    public int getCount() {
        return count;
    }


}
