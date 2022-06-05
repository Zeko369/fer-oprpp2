package hr.fer.oprpp2.dao;

import java.io.Serial;

public class DAOException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
