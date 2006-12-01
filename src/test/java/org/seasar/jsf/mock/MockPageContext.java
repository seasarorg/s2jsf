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
package org.seasar.jsf.mock;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yone
 */
public class MockPageContext extends NullPageContext {

    private Map attr = new HashMap();

    private ServletRequest request;

    private ServletResponse response;

    private HttpSession session;

    public void setAttribute(String arg0, Object arg1) {
        attr.put(arg0, arg1);
    }

    public void setAttribute(String arg0, Object arg1, int arg2) {
        attr.put(arg0, arg1);
    }

    public Object getAttribute(String arg0) {
        return attr.get(arg0);
    }

    public Object getAttribute(String arg0, int arg1) {
        return attr.get(arg0);
    }

    public void removeAttribute(String arg0, int arg1) {
        attr.remove(arg0);
    }

    public void setRequest(ServletRequest request) {
        this.request = request;
    }

    public ServletRequest getRequest() {
        return request;
    }

    public ServletResponse getResponse() {
        return response;
    }

    public void setResponse(ServletResponse response) {
        this.response = response;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

}