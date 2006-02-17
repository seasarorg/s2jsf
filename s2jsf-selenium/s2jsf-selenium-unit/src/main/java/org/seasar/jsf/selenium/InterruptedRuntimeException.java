package org.seasar.jsf.selenium;

public class InterruptedRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InterruptedRuntimeException(InterruptedException cause) {
        super(cause);
    }

    public InterruptedRuntimeException(String message,
        InterruptedException cause) {
        super(message, cause);
    }

}
