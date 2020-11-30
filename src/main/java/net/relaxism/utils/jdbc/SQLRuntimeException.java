package net.relaxism.utils.jdbc;

public class SQLRuntimeException extends RuntimeException {

    public SQLRuntimeException() {
        super();
    }

    public SQLRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SQLRuntimeException(final String message) {
        super(message);
    }

    public SQLRuntimeException(final Throwable cause) {
        super(cause);
    }

}
