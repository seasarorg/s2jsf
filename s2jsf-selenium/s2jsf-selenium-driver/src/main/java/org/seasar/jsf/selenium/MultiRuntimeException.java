package org.seasar.jsf.selenium;

import org.mortbay.util.MultiException;

public class MultiRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MultiRuntimeException(MultiException cause) {
        super(cause);
    }

    public MultiRuntimeException(String message, MultiException cause) {
        super(message, cause);
    }

}
