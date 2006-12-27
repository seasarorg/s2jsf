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
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.DecodeUtil;
import org.seasar.jsf.util.HTMLEncodeUtil;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.ValueHolderUtil;

/**
 * @author yone
 * 
 */
public class HtmlInputSecretRenderer extends HtmlElementRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Input";

    public static final String RENDERER_TYPE = "javax.faces.Secret";

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
    }

    public void encodeEnd(final FacesContext context,
            final UIComponent component) throws IOException {
        if (context == null) {
            throw new NullPointerException("context");
        }
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlInputSecretEnd(context, (HtmlInputSecret) component);
    }

    protected void encodeHtmlInputSecretEnd(final FacesContext context,
            final HtmlInputSecret htmlInputSecret) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        String clientId = htmlInputSecret.getClientId(context);
        String value = ValueHolderUtil.getValueAsString(context,
                htmlInputSecret);
        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputSecret);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.PASSWORD_VALUE, null);
        RenderUtil.renderIdIfNecessary(writer, htmlInputSecret, context);
        writer.writeAttribute(JsfConstants.NAME_ATTR, clientId, null);
        if (!htmlInputSecret.isRedisplay()) {
            value = "";
        }
        writer.writeAttribute(JsfConstants.VALUE_ATTR, HTMLEncodeUtil.encode(
                value, true, true), null);
        RenderUtil.renderAttributes(writer, htmlInputSecret,
                JsfConstants.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if (htmlInputSecret.isDisabled()) {
            writer.writeAttribute(JsfConstants.DISABLED_ATTR,
                    JsfConstants.DISABLED_VALUE, null);
        }
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    public void decode(FacesContext context, UIComponent component) {
        DecodeUtil.decode(context, component);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {

        return RenderUtil.getConvertedUIOutputValue(context,
                (UIOutput) component, submittedValue);
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