package org.seasar.jsf.webapp;

import java.io.InputStream;

import junit.framework.TestCase;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.webapp.WebappConfig;
import org.seasar.jsf.webapp.WebappConfigBuilder;

public class WebappConfigBuilderTest extends TestCase {

    public void testContextParam() throws Exception {
        // ## Arrange ##
        WebappConfigBuilder builder = new WebappConfigBuilder();
        InputStream is = ResourceUtil.getResourceAsStream(getClass()
            .getPackage().getName().replace('.', '/')
            + "/WebappConfigBuilderTest-web.xml");

        // ## Act ##
        WebappConfig webappConfig = builder.build(is);
        is.close();

        // ## Assert ##
        assertNotNull("webappConfig����������邱��", webappConfig);
        ContextParam contextParam = webappConfig
            .getContextParam("javax.faces.CONFIG_FILES");
        assertNotNull("contextParam[javax.faces.CONFIG_FILES]���擾�ł��邱��",
            contextParam);

        assertEquals("context-param/param-value���擾�ł��邱��",
            "/WEB-INF/faces-config.xml", contextParam.getParamValue());
        assertEquals("context-param/param-name���擾�ł��邱��",
            "javax.faces.CONFIG_FILES", contextParam.getParamName());

        assertEquals(
            "javax.faces.DEFAULT_SUFFIX��context-param/param-value���擾�ł��邱��",
            ".html", webappConfig.getContextParam("javax.faces.DEFAULT_SUFFIX")
                .getParamValue());
    }

}
