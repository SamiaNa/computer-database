package com.excilys.java.formation.page;

import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public abstract class Page {

    protected int offset;
    protected int size;
    protected int count;
    protected int number;

    protected final static int DEFAULT_SIZE = 10;

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

}
