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

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.seasar.framework.util.InputStreamUtil;
import org.seasar.jsf.webapp.Taglib;
import org.seasar.jsf.webapp.WebappConfigManager;

/**
 * @author manhole
 */
public class WebappConfigTaglibManagerImpl extends AbstractTaglibManager {

    private WebappConfigManager webappConfigManager;

    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void init() {
        List taglibs = webappConfigManager.getWebappConfig().getTaglibs();
        for (Iterator it = taglibs.iterator(); it.hasNext();) {
            Taglib tablib = (Taglib) it.next();
            String taglibLocation = tablib.getTaglibLocation();
            InputStream is = servletContext.getResourceAsStream(taglibLocation);
            try {
                scanTld(is);
            } finally {
                InputStreamUtil.close(is);
            }
        }
    }

    public void setWebappConfigManager(WebappConfigManager webappConfigManager) {
        this.webappConfigManager = webappConfigManager;
    }

}
