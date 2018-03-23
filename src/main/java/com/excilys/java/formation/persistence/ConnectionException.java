package com.excilys.java.formation.persistence;

public class ConnectionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 2244997669486603724L;

    public ConnectionException() {
        super();
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Exception e) {
        super(e);
    }

}
