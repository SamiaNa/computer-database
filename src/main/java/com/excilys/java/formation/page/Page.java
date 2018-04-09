package com.excilys.java.formation.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public abstract class Page {


    protected int offset;
    protected int size;
    protected int count;
    protected int number;

    protected static final int DEFAULT_SIZE = 10;

    public abstract void nextPage() throws ServiceException, ValidatorException;

    public abstract void prevPage() throws ServiceException, ValidatorException;

    public abstract void getPage(int pageNumber, int pageSize) throws ServiceException, ValidatorException;

    public abstract void getPage(String name, int pageNumber, int pageSize) throws ServiceException, ValidatorException;

    public int getSize() {
        return size;
    }

    public int getNumber() {
        return number;
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
        if (number <= 0) {
            number = 0;
        } else if ((number - 1) * size <= dbSize) {
            number = ((number - 1) * size);
        } else {
            number = dbSize - size;
        }
        return number;
    }

    public int getCount() {
        return count;
    }

    public abstract void getPageOrder(String orderCriteria, String order, int pageNumber, int pageSize)
            throws ValidatorException, ServiceException;

    public abstract void getPageOrder(String orderCriteria, String order, String search, int pageNumber, int pageSize)
            throws ValidatorException, ServiceException;

}
