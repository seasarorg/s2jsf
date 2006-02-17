/*
 * Copyright (c) 2005 ARK Systems Co.,Ltd. All rights reserved.
 */

package org.seasar.jsf.selenium;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author manhole
 */
public class NoCacheFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponseUtil
                    .setNoCacheHeader((HttpServletResponse) response);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

}
