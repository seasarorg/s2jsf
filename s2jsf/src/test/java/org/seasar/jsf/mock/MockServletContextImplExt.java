package org.seasar.jsf.mock;

import java.net.MalformedURLException;
import java.net.URL;

import org.seasar.framework.mock.servlet.MockServletContextImpl;

/**
 * @author yone
 */
public class MockServletContextImplExt extends MockServletContextImpl {

    public MockServletContextImplExt() {
        super(null);
    }

    public MockServletContextImplExt(String path) {
        super(path);

    }

    private static final long serialVersionUID = 1L;

    public URL getResource(String path) throws MalformedURLException {
        URL url = super.getResource(path);
        return (url != null) ? url : new URL(path);
    }

}