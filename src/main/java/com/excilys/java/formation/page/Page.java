package com.excilys.java.formation.page;

import com.excilys.java.formation.persistence.ConnectionException;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.validator.ValidatorException;

public abstract class Page {

    protected int pageNumber;
    protected int size;
    protected int count;

    protected final static int DEFAULT_SIZE = 10;

    public abstract void nextPage() throws ConnectionException, DAOException, ValidatorException;

    public abstract void prevPage() throws  ConnectionException, DAOException, ValidatorException;

    public abstract void getPage(int pageNumber, int pageSize) throws ConnectionException, DAOException, ValidatorException  ;


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

    protected int getOffset(int pageNumber, int pageSize) throws ConnectionException, DAOException {
        int dbSize = ComputerService.INSTANCE.count();
        int offset = 0;
        if ((pageNumber - 1) * pageSize <= dbSize) {
            offset = ((pageNumber - 1) * pageSize);
        } else {
            offset = dbSize - pageSize;
        }

        return offset;
    }


    public int getCount() {
        return count;
    }



}
