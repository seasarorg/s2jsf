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
            ".html", webappConfig.getContextParam("javax.faces.DEFAULT_SUFFIX")
                .getParamValue());
    }

}
