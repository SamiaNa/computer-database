package com.excilys.formation.service.page;

import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.excilys.formation.service.service.ServiceException;
import com.excilys.formation.service.validator.ValidatorException;

public abstract class Page {

    protected long offset;
    protected long size;
    protected long count;
    protected long number;

    protected static final long DEFAULT_SIZE = 10;

    public abstract void nextPage() throws ValidatorException, ServiceException;

    public abstract void prevPage() throws  ValidatorException, ServiceException;

    public abstract void getPage(long pageNumber, long pageSize) throws ValidatorException, ServiceException;

    public long getSize() {
        return size;
    }

    public long getNumber() {
        return number;
    }

    public long offsetNextPage(long dbSize) {
        if (offset + size <= dbSize) {
            offset += size;
        }
        return offset;
    }

    public long offsetPrevPage() {
        offset -= size;
        if (offset < 0) {
            offset = 0;
        }
        return offset;
    }

    public long getNumberOfPages() {
        return (this.count / this.size) + ((this.count % this.size) == 0 ? 0 : 1);
    }

    public long offsetGetPage(long number, long dbSize) {
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

    public long getCount() {
        return count;
    }

    public abstract void getPage(String orderCriteria, String order, String search, long pageNumber, long pageSize)
            throws ValidatorException, ServiceException, DAOException;

}
