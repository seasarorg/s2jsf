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
package org.seasar.jsf.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.framework.container.ExternalContext;
import org.seasar.framework.container.S2Container;

/**
 * @author manhole
 */
public class S2ContainerUtil {

    public static HttpServletRequest getHttpServletRequest(S2Container container) {
        ExternalContext externalContext = container.getExternalContext();
        Object request = externalContext.getRequest();
        return (HttpServletRequest) request;
    }

    public static HttpSession getHttpSession(S2Container container) {
        ExternalContext externalContext = container.getExternalContext();
        Object session = externalContext.getSession();
        return (HttpSession) session;
    }

}
