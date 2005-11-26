package org.seasar.jsf.webapp;

import org.seasar.extension.unit.S2TestCase;

/**
 * @author manhole
 */
public class ServletContextWebappConfigBuilderTest extends S2TestCase {

    private ServletContextWebappConfigManagerImpl manager_;

    protected void setUp() throws Exception {
        include("jsf.dicon");
    }

    public void testBuild() throws Exception {
        WebappConfig webappConfig = manager_.getWebappConfig();
        assertNotNull(webappConfig);
    }

}
