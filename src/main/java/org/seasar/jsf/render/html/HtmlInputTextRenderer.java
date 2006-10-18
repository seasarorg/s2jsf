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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.html.S2HtmlInputText;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.UIValueUtil;

/**
 * @author yone
 * 
 */
public class HtmlInputTextRenderer extends HtmlElementRenderer {

    /**
     * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
        renderS2HtmlInputText(facesContext, (S2HtmlInputText) component);
    }

    protected void renderS2HtmlInputText(FacesContext context,
            S2HtmlInputText component) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        Converter converter = component.getConverter();

        String clientId = component.getClientId(context);
        String value = UIValueUtil.getValueAsString(context, component,
                component.getValue(), converter);
        writer.startElement(JsfConstants.INPUT_ELEM, component);
        writer.writeAttribute(JsfConstants.TYPE_ATTR, JsfConstants.TEXT_VALUE,
                null);
        RenderUtil.renderIdIfNecessary(writer, component, context);
        writer.writeAttribute(JsfConstants.NAME_ATTR, clientId, null);
        if (value != null) {
            writer.writeAttribute(JsfConstants.VALUE_ATTR, value, null);
        }
        RenderUtil.renderAttributes(writer, component,
                JsfConstants.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        String autocomplete = component.getAutocomplete();
        if (autocomplete != null) {
            writer.writeAttribute(JsfConstants.AUTOCOMPLETE_ATTR, autocomplete,
                    null);
        }
        if (component.isDisabled()) {
            writer.writeAttribute(JsfConstants.DISABLED_ATTR,
                    JsfConstants.DISABLED_VALUE, null);
        }
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    /**
     * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {

    }

    /**
     * @see javax.faces.render.Renderer#getRendersChildren()
     */
    public boolean getRendersChildren() {
        return false;
    }

    /**
     * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
    }
}