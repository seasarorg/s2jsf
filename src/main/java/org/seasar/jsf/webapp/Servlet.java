/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import java.util.HashMap;
import java.util.Map;

/**
 * @author manhole
 */
public class Servlet {

    private String servletName_;
    private String servletClass_;
    private String loadOnStartup_;
    private Map initParams_ = new HashMap();

    public String getServletName() {
        return servletName_;
    }

    public void setServletName(String servletName) {
        servletName_ = servletName;
    }

    public String getServletClass() {
        return servletClass_;
    }

    public void setServletClass(String servletClass) {
        servletClass_ = servletClass;
    }

    public String getLoadOnStartup() {
        return loadOnStartup_;
    }

    public void setLoadOnStartup(String loadOnStartup) {
        loadOnStartup_ = loadOnStartup;
    }

    public InitParam getInitParam(String paramName) {
        return (InitParam) initParams_.get(paramName);
    }

    public void addInitParam(InitParam initParam) {
        initParams_.put(initParam.getParamName(), initParam);
    }

}
