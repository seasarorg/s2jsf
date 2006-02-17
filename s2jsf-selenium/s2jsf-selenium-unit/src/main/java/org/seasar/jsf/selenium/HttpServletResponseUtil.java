package org.seasar.jsf.selenium;

import javax.servlet.http.HttpServletResponse;

/**
 * @author manhole
 */
public class HttpServletResponseUtil {

    public static void setNoCacheHeader(HttpServletResponse response) {
        // Pragma: No-cache
        // Cache-Control: no-cache
        // Expires: Thu, 01 Jan 1970 00:00:00 GMT
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 1L);
    }

}
