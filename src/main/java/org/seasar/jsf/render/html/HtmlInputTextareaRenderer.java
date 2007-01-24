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

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.renderkit.JSFAttr;
import org.apache.myfaces.renderkit.RendererUtils;
import org.apache.myfaces.renderkit.html.HTML;
import org.apache.myfaces.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.HtmlTextareaRendererBase;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.html.S2HtmlInputTextarea;
import org.seasar.jsf.util.DecodeUtil;
import org.seasar.jsf.util.RenderUtil;

/**
 * @author shot
 * @author yone
 */
public class HtmlInputTextareaRenderer extends HtmlTextareaRendererBase {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        RendererUtils.checkParamValidity(context, component, UIInput.class);
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(HTML.TEXTAREA_ELEM, component);
        final String clientId = component.getClientId(context);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);
        HtmlRendererUtils.writeIdIfNecessary(writer, component, context);
        HtmlRendererUtils.renderHTMLAttributes(writer, component,
                HTML.TEXTAREA_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if (isDisabled(context, component)) {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }
        if (component instanceof S2HtmlInputTextarea) {
            S2HtmlInputTextarea textarea = (S2HtmlInputTextarea) component;
            final String wrap = textarea.getWrap();
            if (wrap != null) {
                writer.writeAttribute(JsfConstants.WRAP_ATTR, wrap, null);
            }
        }
        String value = RendererUtils.getStringValue(context, component);
        writer.writeText(value, JSFAttr.VALUE_ATTR);
        writer.endElement(HTML.TEXTAREA_ELEM);
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

}
