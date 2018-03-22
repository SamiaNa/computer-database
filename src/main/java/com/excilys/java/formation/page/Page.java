package com.excilys.java.formation.page;

import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.ComputerService;

public abstract class Page {

    public abstract void printPage(int pageNumber, int pageSize) throws ClassNotFoundException, DAOException;

    protected int getOffset(int pageNumber, int pageSize) throws ClassNotFoundException, DAOException {
        int dbSize = ComputerService.INSTANCE.count();
        int offset = 0;
        if ((pageNumber - 1) * pageSize <= dbSize) {
            offset = ((pageNumber - 1) * pageSize);
        } else {
            offset = dbSize - pageSize;
        }

        return offset;
    }
}
