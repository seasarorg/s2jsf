/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.jsf.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.framework.util.ResourceUtil;

/**
 * @author manhole
 */
public class WebappConfigBuilderTest extends TestCase {

    public void testContextParam() throws Exception {
        // ## Act ##
        WebappConfig webappConfig = buildWebappConfig();

        // ## Assert ##
        assertNotNull("webappConfigが生成されること", webappConfig);
        ContextParam contextParam = webappConfig
                .getContextParam("javax.faces.CONFIG_FILES");
        assertNotNull("contextParam[javax.faces.CONFIG_FILES]を取得できること",
                contextParam);

        assertEquals("context-param/param-valueを取得できること",
                "/WEB-INF/faces-config.xml", contextParam.getParamValue());
        assertEquals("context-param/param-nameを取得できること",
                "javax.faces.CONFIG_FILES", contextParam.getParamName());

        assertEquals(
                "javax.faces.DEFAULT_SUFFIXのcontext-param/param-valueを取得できること",
                ".html", webappConfig.getContextParam(
                        "javax.faces.DEFAULT_SUFFIX").getParamValue());
    }

    public void testServlet() throws Exception {
        // ## Act ##
        WebappConfig webappConfig = buildWebappConfig();

        // ## Assert ##
        {
            Servlet servlet = webappConfig.getServlet("Faces Servlet");
            assertEquals("servlet/servlet-nameを取得できること", "Faces Servlet",
                    servlet.getServletName());
            assertEquals("servlet/servlet-classを取得できること",
                    "javax.faces.webapp.FacesServlet", servlet
                            .getServletClass());
            assertEquals("servlet/load-on-startupを取得できること", "2", servlet
                    .getLoadOnStartup());
        }
        {
            Servlet servlet = webappConfig.getServlet("s2servlet");
            InitParam initParam = servlet.getInitParam("debug");
            assertNotNull("servlet/init-paramを取得できること", initParam);
            assertEquals("servlet/init-param/param-nameを取得できること", "debug",
                    initParam.getParamName());
            assertEquals("servlet/init-param/param-valueを取得できること", "true",
                    initParam.getParamValue());
        }
    }

    public void testTaglib() throws Exception {
        // ## Act ##
        WebappConfig webappConfig = buildWebappConfig();

        // ## Assert ##
        List taglibs = webappConfig.getTaglibs();
        assertEquals(1, taglibs.size());

        Taglib taglib = (Taglib) taglibs.get(0);
        assertEquals("/tags/struts-bean", taglib.getTaglibUri());
        assertEquals("/WEB-INF/struts-bean.tld", taglib.getTaglibLocation());
    }

    private WebappConfig buildWebappConfig() throws IOException {
        // ## Arrange ##
        WebappConfigBuilder builder = new WebappConfigBuilder();
        InputStream is = ResourceUtil.getResourceAsStream(getClass()
                .getPackage().getName().replace('.', '/')
                + "/WebappConfigBuilderTest-web.xml");

        // ## Act ##
        WebappConfig webappConfig = builder.build(is);
        is.close();
        return webappConfig;
    }

}
