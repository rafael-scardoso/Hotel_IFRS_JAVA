package db_control;

import java.io.Serial;

public class DbException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DbException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
