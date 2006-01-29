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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.RenderUtil;

/**
 * @author manhole
 */
public abstract class AbstractHtmlMessageRenderer extends Renderer {

    protected void renderSingleFacesMessage(FacesContext context,
            UIComponent component, FacesMessage facesMessage)
            throws IOException {

        String style = getStyle(component, facesMessage.getSeverity());
        String styleClass = getStyleClass(component, facesMessage.getSeverity());

        String summary = facesMessage.getSummary();
        String detail = facesMessage.getDetail();

        String title = getTitle(component);
        boolean tooltip = isTooltip(component);

        if (title == null && tooltip) {
            title = summary;
        }

        ResponseWriter writer = context.getResponseWriter();

        boolean isWriteSpan = false;

        if (component.getId() != null
                && !component.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {

            writer.startElement(JsfConstants.SPAN_ELEM, component);
            isWriteSpan = true;

            RenderUtil.renderIdIfNecessary(writer, component, context);
            RenderUtil
                    .renderAttributes(
                            writer,
                            component,
                            JsfConstants.MESSAGE_PASSTHROUGH_ATTRIBUTES_WITHOUT_TITLE_STYLE_AND_STYLE_CLASS);
        } else {
            isWriteSpan = RenderUtil
                    .renderAttributesWithOptionalStartElement(
                            writer,
                            component,
                            JsfConstants.SPAN_ELEM,
                            JsfConstants.MESSAGE_PASSTHROUGH_ATTRIBUTES_WITHOUT_TITLE_STYLE_AND_STYLE_CLASS);
        }

        isWriteSpan |= RenderUtil.renderAttributeWithOptionalStartElement(
                writer, component, JsfConstants.SPAN_ELEM,
                JsfConstants.TITLE_ATTR, title, isWriteSpan);
        isWriteSpan |= RenderUtil.renderAttributeWithOptionalStartElement(
                writer, component, JsfConstants.SPAN_ELEM,
                JsfConstants.STYLE_ATTR, style, isWriteSpan);
        isWriteSpan |= RenderUtil.renderAttributeWithOptionalStartElement(
                writer, component, JsfConstants.SPAN_ELEM,
                JsfConstants.STYLE_CLASS_ATTR, styleClass, isWriteSpan);

        boolean showSummary = isShowSummary(component) && (summary != null);
        boolean showDetail = isShowDetail(component) && (detail != null);

        if (showSummary && !(title == null && tooltip)) {
            writer.write(summary);
            if (showDetail) {
                writer.write(" ");
            }
        }

        if (showDetail) {
            writer.write(detail);
        }

        if (isWriteSpan) {
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
    }

    protected abstract boolean isShowDetail(UIComponent component);

    protected abstract boolean isShowSummary(UIComponent component);

    protected abstract boolean isTooltip(UIComponent component);

    protected abstract String getTitle(UIComponent component);

    protected abstract String getStyleClass(UIComponent component,
            FacesMessage.Severity severity);

    protected abstract String getStyle(UIComponent component,
            FacesMessage.Severity severity);

}
