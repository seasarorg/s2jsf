/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.jsf.runtime;

import junit.framework.TestCase;

import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.jsf.TaglibConfig;
import org.seasar.jsf.webapp.MockWebappConfigManager;
import org.seasar.jsf.webapp.Taglib;
import org.seasar.jsf.webapp.WebappConfigImpl;

/**
 * @author manhole
 */
public class WebappConfigTaglibManagerImplTest extends TestCase {

    public void test1() throws Exception {
        // ## Arrange ##
        final WebappConfigImpl webappConfig = new WebappConfigImpl();
        Taglib taglib = new Taglib();
        taglib.setTaglibUri("http://java.sun.com/jsf/html");
        taglib.setTaglibLocation("org/seasar/jsf/runtime/myfaces-html.tld");
        webappConfig.addTaglib(taglib);
        MockWebappConfigManager webappConfigManager = new MockWebappConfigManager();
        webappConfigManager.setWebappConfig(webappConfig);

        WebappConfigTaglibManagerImpl taglibManager = new WebappConfigTaglibManagerImpl();
        taglibManager.setWebappConfigManager(webappConfigManager);
        taglibManager.setServletContext(new MockServletContextImpl(null));

        // ## Act ##
        taglibManager.init();

        // ## Assert ##
        assertEquals(true, taglibManager
                .hasTaglibConfig("http://java.sun.com/jsf/html"));
        TaglibConfig taglibConfig = taglibManager
                .getTaglibConfig("http://java.sun.com/jsf/html");
        assertEquals("http://java.sun.com/jsf/html", taglibConfig.getUri());
    }

}
