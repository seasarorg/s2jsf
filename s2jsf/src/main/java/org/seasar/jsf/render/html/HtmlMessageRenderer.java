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
package org.seasar.jsf.render.html;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessage;
import javax.faces.context.FacesContext;

/**
 * @author manhole
 */
public class HtmlMessageRenderer extends AbstractHtmlMessageRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        renderMessage(context, (HtmlMessage) component);
    }

    protected void renderMessage(FacesContext context, HtmlMessage message)
            throws IOException {
        String forAttr = message.getFor();
        if (forAttr == null) {
            throw new FacesException("[for] is null");
        }

        UIComponent forComponent = message.findComponent(forAttr);
        if (forComponent == null) {
            throw new FacesException("forComponent");
        }

        String clientId = forComponent.getClientId(context);

        Iterator messageIterator = context.getMessages(clientId);
        if (!messageIterator.hasNext()) {
            return;
        }

        FacesMessage facesMessage = (FacesMessage) messageIterator.next();

        renderSingleFacesMessage(context, message, facesMessage);
    }

    protected boolean isShowDetail(UIComponent component) {
        return ((HtmlMessage) component).isShowDetail();
    }

    protected boolean isShowSummary(UIComponent component) {
        return ((HtmlMessage) component).isShowSummary();
    }

    protected boolean isTooltip(UIComponent component) {
        return ((HtmlMessage) component).isTooltip();
    }

    protected String getTitle(UIComponent component) {
        return ((HtmlMessage) component).getTitle();
    }

    protected String getStyleClass(UIComponent component, Severity severity) {
        HtmlMessage message = (HtmlMessage) component;
        String styleClass = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            styleClass = message.getInfoClass();
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            styleClass = message.getWarnClass();
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            styleClass = message.getErrorClass();
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            styleClass = message.getFatalClass();
        }
        if (styleClass == null) {
            styleClass = message.getStyleClass();
        }
        return styleClass;
    }

    protected String getStyle(UIComponent component, Severity severity) {
        HtmlMessage message = (HtmlMessage) component;
        String style = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            style = message.getInfoStyle();
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            style = message.getWarnStyle();
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            style = message.getErrorStyle();
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            style = message.getFatalStyle();
        }
        if (style == null) {
            style = message.getStyle();
        }
        return style;
    }

}
