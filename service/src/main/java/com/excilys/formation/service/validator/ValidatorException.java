package com.excilys.formation.service.validator;


public class ValidatorException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ValidatorException() {
        super();
    }

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(Exception e) {
        super(e);
    }
}
