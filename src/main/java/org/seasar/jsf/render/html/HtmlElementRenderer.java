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
package org.seasar.jsf.render.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.jsf.component.UIElement;
import org.seasar.jsf.util.BindingUtil;
import org.seasar.jsf.util.RenderUtil;

/**
 * @author higa
 * @author yone
 */
public class HtmlElementRenderer extends Renderer {

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {

        if (!component.isRendered()) {
            return;
        }
        ResponseWriter writer = facesContext.getResponseWriter();
        if (!(component instanceof UIElement)) {
            throw new IllegalArgumentException("not UIElementNode");
        }
        UIElement elem = (UIElement) component;
        String tagName = elem.getTagName();
        writer.startElement(tagName, component);
        if (elem.isIdSet()) {
            RenderUtil.renderIdIfNecessary(writer, component, facesContext);
        }
        renderAttributes(writer, elem);
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {

        UIElement elem = (UIElement) component;
        ResponseWriter writer = facesContext.getResponseWriter();
        if (component.isRendered()) {
            writer.endElement(elem.getTagName());
        }
        String afterContents = elem.getAfterContents();
        if (afterContents != null) {
            writer.write(afterContents);
        }
    }

    /**
     * @see javax.faces.render.Renderer#getRendersChildren()
     */
    public boolean getRendersChildren() {
        return true;
    }

    /**
     * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {

        if (!component.isRendered()) {
            return;
        }
        RenderUtil.encodeChildren(context, component);
    }

    protected void renderAttributes(ResponseWriter writer, UIElement component)
            throws IOException {
        Map attrs = component.getAttributes();
        for (Iterator i = attrs.keySet().iterator(); i.hasNext();) {
            String attrName = (String) i.next();
            if (attrName.indexOf('.') > 0) {
                continue;
            }
            Object value = component.getAttributes().get(attrName);
            RenderUtil.renderAttribute(writer, attrName, value, attrName);
        }
        String[] bindingPropertyNames = component.getBindingPropertyNames();
        for (int i = 0; i < bindingPropertyNames.length; ++i) {
            String name = bindingPropertyNames[i];
            Object value = BindingUtil.getBindingValue(component, name);
            RenderUtil.renderAttribute(writer, name, value, name);
        }
    }
}