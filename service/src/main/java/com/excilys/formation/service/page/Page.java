package com.excilys.formation.service.page;

import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.excilys.formation.service.service.ServiceException;
import com.excilys.formation.service.validator.ValidatorException;

public abstract class Page {

    protected int offset;
    protected int size;
    protected int count;
    protected int number;

    protected static final int DEFAULT_SIZE = 10;

    public abstract void nextPage() throws ServiceException, ValidatorException;

    public abstract void prevPage() throws ServiceException, ValidatorException;

    public abstract void getPage(int pageNumber, int pageSize) throws ServiceException, ValidatorException;

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

    public abstract void getPage(String orderCriteria, String order, String search, int pageNumber, int pageSize)
            throws ValidatorException, ServiceException, DAOException;

}
