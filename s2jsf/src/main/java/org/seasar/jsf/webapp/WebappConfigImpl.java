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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WebappConfigImpl implements WebappConfig, Serializable {

    private static final long serialVersionUID = 1L;

    private Map contextParams_ = new HashMap();

    public ContextParam getContextParam(String name) {
        return (ContextParam) contextParams_.get(name);
    }

    public void addContextParam(ContextParam contextParam) {
        contextParams_.put(contextParam.getParamName(), contextParam);
    }

}
