package org.seasar.selenium;

import javax.servlet.http.HttpServletResponse;

/**
 * @author manhole
 */
public class HttpServletResponseUtil {

    public static void setNoCacheHeader(HttpServletResponse response) {
        // Pragma: no-cache
        // Cache-Control: no-cache
        // Expires: Thu, 01 Jan 1970 00:00:00 GMT
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        //response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
        response.setDateHeader("Expires", 1);
    }

}
