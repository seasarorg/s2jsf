/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.jsf.JsfConfig;

/**
 * @author manhole
 */
public class ServletContextWebappConfigManagerImpl implements
        WebappConfigManager {

    private ServletContext servletContext_;

    private WebappConfig webappConfig_;

    private JsfConfig jsfConfig_;

    public void init() {
        InputStream is = servletContext_
                .getResourceAsStream("/WEB-INF/web.xml");
        if (is == null) {
            throw new ResourceNotFoundRuntimeException("/WEB-INF/web.xml");
        }
        try {
            webappConfig_ = new WebappConfigBuilder().build(is);
        } finally {
            InputStreamUtil.close(is);
        }
        ContextParam allowJavascript = webappConfig_
                .getContextParam("org.apache.myfaces.ALLOW_JAVASCRIPT");
        if (allowJavascript != null) {
            jsfConfig_.setAllowJavascript(Boolean.valueOf(
                    allowJavascript.getParamValue()).booleanValue());
        }
    }

    public void setServletContext(ServletContext servletContext) {
        servletContext_ = servletContext;
    }

    public WebappConfig getWebappConfig() {
        return webappConfig_;
    }

    public void setJsfConfig(JsfConfig jsfConfig) {
        jsfConfig_ = jsfConfig;
    }

}
