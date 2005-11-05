/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.render.Renderer;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.UIValueUtil;

/**
 * @author higa
 *
 */
public class HtmlSelectRenderer extends Renderer {

    protected void renderSelectOptions(FacesContext context,
            UIOutput component, List selectItemList, List lookupAsString)
            throws IOException {

        for (Iterator it = selectItemList.iterator(); it.hasNext();) {
            SelectItem selectItem = (SelectItem) it.next();
            renderSelectItem(context, component, selectItem, lookupAsString);
        }
    }

    protected void renderSelectItem(FacesContext context, UIOutput component,
            SelectItem selectItem, List lookupAsString) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        Converter converter = component.getConverter();
        if (selectItem instanceof SelectItemGroup) {
            writer.startElement(JsfConstants.OPTGROUP_ELEM, null);
            writer.writeAttribute(JsfConstants.LABEL_ATTR, selectItem
                    .getLabel(), null);
            SelectItem[] selectItems = ((SelectItemGroup) selectItem)
                    .getSelectItems();
            renderSelectOptions(context, component, Arrays.asList(selectItems),
                    lookupAsString);
            writer.endElement(JsfConstants.OPTGROUP_ELEM);
        } else {
            Object itemValue = selectItem.getValue();
            String itemStrValue = UIValueUtil.getValueAsString(context,
                    component, itemValue, converter);
            writer.write("\t\t");
            writer.startElement(JsfConstants.OPTION_ELEM, null);
            if (itemStrValue != null) {
                writer.writeAttribute(JsfConstants.VALUE_ATTR, itemStrValue,
                        null);
            }
            if (lookupAsString.contains(itemStrValue)) {
                writer.writeAttribute(JsfConstants.SELECTED_ATTR,
                        JsfConstants.SELECTED_VALUE, null);
            }

            writer.writeText(selectItem.getLabel(), null);

            if (selectItem.isDisabled()) {
                writer.writeAttribute(JsfConstants.DISABLED_ATTR,
                        JsfConstants.DISABLED_VALUE, null);
            }

            writer.endElement(JsfConstants.OPTION_ELEM);
        }
    }
}