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
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;
import javax.faces.render.Renderer;

import org.seasar.framework.util.StringUtil;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.SelectItemUtil;
import org.seasar.jsf.util.UIComponentUtil;
import org.seasar.jsf.util.UIValueUtil;
import org.seasar.jsf.util.ValueHolderUtil;

/**
 * @author higa
 *  
 */
public class HtmlInputRadioRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {

        if (!component.isRendered()) {
            return;
        }
        HtmlSelectOneRadio parent = (HtmlSelectOneRadio) UIComponentUtil
                .findParent(component, HtmlSelectOneRadio.class);
        String valueStr = ValueHolderUtil.getValueAsString(context, parent);
        String clientId = parent.getClientId(context);
        Converter converter = RenderUtil.findConverterForSubmittedValue(
                context, parent);
        UISelectItem uiSelectItem = (UISelectItem) component;
        SelectItem selectItem = SelectItemUtil.getSelectItem(uiSelectItem);
        Object itemValue = selectItem.getValue();
        String itemValueStr = UIValueUtil.getValueAsString(context,
                component, itemValue, converter);
        boolean disabled = parent.isDisabled() ? true : selectItem.isDisabled(); 
        renderRadio(context, uiSelectItem, itemValueStr, selectItem
                .getLabel(), clientId, valueStr.equals(itemValueStr),
                disabled);
    }

    protected void renderRadio(FacesContext context, UISelectItem component,
            String value, String label, String name, boolean checked, boolean disabled)
            throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.INPUT_ELEM, component);
        writer.writeAttribute(JsfConstants.TYPE_ATTR, JsfConstants.RADIO_VALUE,
                null);
        writer.writeAttribute(JsfConstants.ID_ATTR, component.getClientId(context), null);
        writer.writeAttribute(JsfConstants.NAME_ATTR, name, null);
        if (checked) {
            writer.writeAttribute(JsfConstants.CHECKED_ATTR,
                    JsfConstants.CHECKED_ATTR, null);
        }
        if (!StringUtil.isEmpty(value)) {
            writer.writeAttribute(JsfConstants.VALUE_ATTR, value, null);
        }
        RenderUtil.renderAttributes(writer, component,
                JsfConstants.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if (disabled) {
            writer.writeAttribute(JsfConstants.DISABLED_ATTR, Boolean.TRUE, null);
        }
        /*
        if (!StringUtil.isEmpty(label)) {
            writer.write(JsfConstants.NBSP_ENTITY);
            writer.writeText(label, null);
        }
        */
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {

        return RenderUtil.getConvertedUIOutputValue(context,
                (UIOutput) component, submittedValue);
    }
}