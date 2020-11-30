package net.relaxism.utils.jdbc;

public class SQLRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1526194939656995561L;

    public SQLRuntimeException() {
        super();
    }

    public SQLRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLRuntimeException(String message) {
        super(message);
    }

    public SQLRuntimeException(Throwable cause) {
        super(cause);
    }

}
