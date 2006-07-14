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
import javax.faces.component.html.HtmlMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.RenderUtil;

/**
 * @author manhole
 */
public class HtmlMessagesRenderer extends AbstractHtmlMessageRenderer {

    protected static final String LAYOUT_LIST = "list";

    protected static final String LAYOUT_TABLE = "table";

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        renderMessages(context, (HtmlMessages) component);
    }

    protected void renderMessages(FacesContext context, HtmlMessages messages)
            throws IOException {
        Iterator messagesIterator;
        if (messages.isGlobalOnly()) {
            messagesIterator = context.getMessages(null);
        } else {
            messagesIterator = context.getMessages();
        }
        if (!messagesIterator.hasNext()) {
            return;
        }
        if (messagesIterator.hasNext()) {
            String layout = messages.getLayout();
            if (layout == null || LAYOUT_LIST.equalsIgnoreCase(layout)) {
                renderList(context, messages, messagesIterator);
            } else if (LAYOUT_TABLE.equalsIgnoreCase(layout)) {
                renderTable(context, messages, messagesIterator);
            } else {
                throw new FacesException("invalid messages layout:" + layout);
            }
        }
    }

    protected void renderList(FacesContext context, HtmlMessages messages,
            Iterator messagesIterator) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(JsfConstants.UL_ELEM, messages);
        RenderUtil.renderIdIfNecessary(writer, messages, context);

        while (messagesIterator.hasNext()) {
            writer.startElement(JsfConstants.LI_ELEM, messages);
            renderSingleFacesMessage(context, messages,
                    (FacesMessage) messagesIterator.next());
            writer.endElement(JsfConstants.LI_ELEM);
        }

        writer.endElement(JsfConstants.UL_ELEM);
    }

    protected void renderTable(FacesContext context, HtmlMessages messages,
            Iterator messagesIterator) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(JsfConstants.TABLE_ELEM, messages);
        RenderUtil.renderIdIfNecessary(writer, messages, context);

        while (messagesIterator.hasNext()) {
            writer.startElement(JsfConstants.TR_ELEM, messages);
            writer.startElement(JsfConstants.TD_ELEM, messages);
            renderSingleFacesMessage(context, messages,
                    (FacesMessage) messagesIterator.next());
            writer.endElement(JsfConstants.TD_ELEM);
            writer.endElement(JsfConstants.TR_ELEM);
        }

        writer.endElement(JsfConstants.TABLE_ELEM);
    }

    protected boolean isShowDetail(UIComponent component) {
        return ((HtmlMessages) component).isShowDetail();
    }

    protected boolean isShowSummary(UIComponent component) {
        return ((HtmlMessages) component).isShowSummary();
    }

    protected boolean isTooltip(UIComponent component) {
        return ((HtmlMessages) component).isTooltip();
    }

    protected String getTitle(UIComponent component) {
        return ((HtmlMessages) component).getTitle();
    }

    protected String getStyleClass(UIComponent component, Severity severity) {
        HtmlMessages messages = (HtmlMessages) component;
        String styleClass = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            styleClass = messages.getInfoClass();
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            styleClass = messages.getWarnClass();
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            styleClass = messages.getErrorClass();
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            styleClass = messages.getFatalClass();
        }
        if (styleClass == null) {
            styleClass = messages.getStyleClass();
        }
        return styleClass;
    }

    protected String getStyle(UIComponent component, Severity severity) {
        HtmlMessages messages = (HtmlMessages) component;
        String style = null;
        if (severity == FacesMessage.SEVERITY_INFO) {
            style = messages.getInfoStyle();
        } else if (severity == FacesMessage.SEVERITY_WARN) {
            style = messages.getWarnStyle();
        } else if (severity == FacesMessage.SEVERITY_ERROR) {
            style = messages.getErrorStyle();
        } else if (severity == FacesMessage.SEVERITY_FATAL) {
            style = messages.getFatalStyle();
        }
        if (style == null) {
            style = messages.getStyle();
        }
        return style;
    }

}
