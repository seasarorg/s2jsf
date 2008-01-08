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
package org.seasar.jsf.mock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

/**
 * @author yone
 */
public class MockFacesContext extends FacesContext {

    private Map messages = new LinkedHashMap();

    private boolean responseComplete = false;

    private ExternalContext externalContext = null;

    public MockFacesContext() {
        setCurrentInstance(this);
    }

    public Iterator getClientIdsWithMessages() {
        return null;
    }

    public Severity getMaximumSeverity() {
        return null;
    }

    public Iterator getMessages() {
        List all = new ArrayList();
        for (Iterator it = messages.values().iterator(); it.hasNext();) {
            List messages = (List) it.next();
            all.addAll(messages);
        }
        return all.iterator();
    }

    public Iterator getMessages(String clientId) {
        return getMessagesList(clientId).iterator();
    }

    public RenderKit getRenderKit() {
        return null;
    }

    public void addMessage(String clientId, FacesMessage message) {
        List l = getMessagesList(clientId);
        l.add(message);
    }

    private List getMessagesList(String clientId) {
        List l = (List) messages.get(clientId);
        if (l == null) {
            l = new ArrayList();
            messages.put(clientId, l);
        }
        return l;
    }

    public Application getApplication() {
        return null;
    }

    public ExternalContext getExternalContext() {
        if (externalContext == null) {
            externalContext = new MockExternalContextImpl();
        }
        return externalContext;
    }

    public boolean getRenderResponse() {
        return false;
    }

    public boolean getResponseComplete() {
        return responseComplete;
    }

    public ResponseStream getResponseStream() {
        return null;
    }

    public void setResponseStream(ResponseStream arg0) {
    }

    public ResponseWriter getResponseWriter() {
        return null;
    }

    public void setResponseWriter(ResponseWriter arg0) {
    }

    public UIViewRoot getViewRoot() {
        return null;
    }

    public void setViewRoot(UIViewRoot arg0) {
    }

    public void release() {
    }

    public void renderResponse() {
    }

    public void responseComplete() {
        responseComplete = true;
    }

}
