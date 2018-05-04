package com.excilys.formation.persistence.daoexceptions;

public class DAOException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 8694835100757588512L;

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Exception e) {
        super(e);
    }


}
