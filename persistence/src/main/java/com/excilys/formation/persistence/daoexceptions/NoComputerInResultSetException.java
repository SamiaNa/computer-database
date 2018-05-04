package com.excilys.formation.persistence.daoexceptions;

public class NoComputerInResultSetException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoComputerInResultSetException() {
        super();
    }

    public NoComputerInResultSetException(String message) {
        super(message);
    }

}
