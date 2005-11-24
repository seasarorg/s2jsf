package org.seasar.jsf.webapp;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.webapp.ContextParam;
import org.seasar.jsf.webapp.ServletContextWebappConfigManagerImpl;
import org.seasar.jsf.webapp.WebappConfig;

public class ServletContextWebappConfigBuilderTest extends S2TestCase {

    private WebappConfig webappConfig_;

    protected void setUp() throws Exception {
        include("jsf.dicon");
    }

    public void testBuild() throws Exception {
        ServletContextWebappConfigManagerImpl manager = new ServletContextWebappConfigManagerImpl();
        manager.setServletContext(getServletContext());
        manager.init();
        WebappConfig webappConfig = manager.getWebappConfig();
        assertNotNull(webappConfig);
    }

    public void testBuildByDicon() throws Exception {
        assertNotNull(webappConfig_);
        ContextParam contextParam = webappConfig_
            .getContextParam("javax.faces.STATE_SAVING_METHOD");
        assertEquals("server", contextParam.getParamValue());
    }

}
