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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;
import javax.faces.render.Renderer;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.DecodeUtil;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.SelectItemUtil;
import org.seasar.jsf.util.UIValueUtil;
import org.seasar.jsf.util.ValueHolderUtil;

/**
 * @author Satoshi Kimura
 */
public class HtmlSelectManyCheckboxRenderer extends Renderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        renderSelectManyCheckbox(context, (HtmlSelectManyCheckbox) component);
    }

    protected void renderSelectManyCheckbox(FacesContext context,
            HtmlSelectManyCheckbox component) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.TABLE_ELEM, component);
        RenderUtil.renderIdIfNecessary(writer, component, context);
        writer.writeAttribute(JsfConstants.NAME_ATTR, component
                .getClientId(context), null);

        boolean pageDirection = JsfConstants.PAGE_DIRECTION_ATTR
                .equals(component.getLayout());
        if (!pageDirection) {
            writer.startElement(JsfConstants.TR_ELEM, null);
        }

        RenderUtil.renderAttributes(writer, component,
                JsfConstants.SELECT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if (component.isDisabled()) {
            writer.writeAttribute(JsfConstants.DISABLED_ATTR, Boolean.TRUE,
                    null);
        }
        renderSelectManyCheckbox(context, component, pageDirection);
        if (!pageDirection) {
            writer.endElement(JsfConstants.TR_ELEM);
        }
        writer.endElement(JsfConstants.TABLE_ELEM);
    }

    protected List getLookupAsString(FacesContext context, UIComponent component) {
        String[] valueStrArray = ValueHolderUtil.getValueAsStringArray(context,
                component);
        List lookupAsString = new ArrayList();
        if (valueStrArray != null) {
            for (int i = 0; i < valueStrArray.length; ++i) {
                lookupAsString.add(valueStrArray[i]);
            }
        }
        return lookupAsString;
    }

    public void decode(FacesContext context, UIComponent component) {
        DecodeUtil.decodeMany(context, component);
    }

    protected void renderSelectManyCheckbox(FacesContext context, UIOutput component,
            boolean pageDirection) throws IOException {

        List selectItemList = SelectItemUtil.getSelectItemList(component);
        List lookupAsString = getLookupAsString(context, component);
        ResponseWriter writer = context.getResponseWriter();

        for (Iterator it = selectItemList.iterator(); it.hasNext();) {
            if (pageDirection) {
                writer.startElement(JsfConstants.TR_ELEM, null);
            }
            writer.startElement(JsfConstants.TD_ELEM, null);
            SelectItem selectItem = (SelectItem) it.next();
            renderCheckbox(context, component, selectItem, lookupAsString);
            writer.endElement(JsfConstants.TD_ELEM);
            if (pageDirection) {
                writer.endElement(JsfConstants.TR_ELEM);
            }
        }
    }

    protected void renderCheckbox(FacesContext context, UIOutput component,
            SelectItem selectItem, List lookupAsString) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        Converter converter = component.getConverter();
        Object itemValue = selectItem.getValue();
        String itemStrValue = UIValueUtil.getValueAsString(context, component,
                itemValue, converter);
        writer.startElement(JsfConstants.LABEL_ATTR, null);
        writer.startElement(JsfConstants.INPUT_ELEM, component);
        writer.writeAttribute(JsfConstants.NAME_ATTR, component
                .getClientId(context), null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.CHECKBOX_VALUE, null);
        if (itemStrValue != null) {
            writer.writeAttribute(JsfConstants.VALUE_ATTR, itemStrValue, null);
        }
        if (lookupAsString.contains(itemStrValue)) {
            writer.writeAttribute(JsfConstants.CHECKED_ATTR,
                    JsfConstants.CHECKED_VALUE, null);
        }
        if (selectItem.isDisabled()) {
            writer.writeAttribute(JsfConstants.DISABLED_ATTR,
                    JsfConstants.DISABLED_VALUE, null);
        }
        writer.endElement(JsfConstants.INPUT_ELEM);
        writer.writeText(selectItem.getLabel(), null);
        writer.endElement(JsfConstants.LABEL_ATTR);
    }
    
    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {
        
        return RenderUtil.getConvertedUIOutputValues(context, (UIOutput) component, submittedValue);
    }
}