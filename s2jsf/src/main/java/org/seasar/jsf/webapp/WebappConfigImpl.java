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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author manhole
 */
public class WebappConfigImpl implements WebappConfig, Serializable {

    private static final long serialVersionUID = 1L;

    private Map contextParams_ = new HashMap();

    private Map servlets_ = new HashMap();

    private List taglibs_ = new ArrayList();

    public ContextParam getContextParam(String name) {
        return (ContextParam) contextParams_.get(name);
    }

    public void addContextParam(ContextParam contextParam) {
        contextParams_.put(contextParam.getParamName(), contextParam);
    }

    public Servlet getServlet(String servletName) {
        return (Servlet) servlets_.get(servletName);
    }

    public void addServlet(Servlet servlet) {
        servlets_.put(servlet.getServletName(), servlet);
    }

    public List getTaglibs() {
        return Collections.unmodifiableList(taglibs_);
    }

    public void addTaglib(Taglib taglib) {
        taglibs_.add(taglib);
    }

}
