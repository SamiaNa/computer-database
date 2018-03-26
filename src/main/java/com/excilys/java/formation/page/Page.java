package com.excilys.java.formation.page;

import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

public abstract class Page {

    protected int pageNumber;
    protected int size;
    protected int count;

    protected final static int DEFAULT_SIZE = 10;

    public abstract void nextPage() throws ServiceException, ValidatorException;

    public abstract void prevPage() throws ServiceException, ValidatorException;

    public abstract void getPage(int pageNumber, int pageSize) throws ServiceException, ValidatorException;

    public int offsetNextPage(int dbSize) {
        if (pageNumber + size <= dbSize) {
            pageNumber += size;
        }
        return pageNumber;
    }

    public int offsetPrevPage() {
        pageNumber -= size;
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        return pageNumber;
    }

    public int offsetGetPage(int pageNumber, int dbSize) {
        if (pageNumber <= 0) {
            pageNumber = 0;
        } else if ((pageNumber - 1) * size <= dbSize) {
            pageNumber = ((pageNumber - 1) * size);
        } else {
            pageNumber = dbSize - size;
        }
        return pageNumber;
    }

    public int getCount() {
        return count;
    }

}
