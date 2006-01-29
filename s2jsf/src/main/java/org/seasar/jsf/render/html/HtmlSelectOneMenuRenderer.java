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
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.DecodeUtil;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.SelectItemUtil;
import org.seasar.jsf.util.ValueHolderUtil;

/**
 * @author higa
 *
 */
public class HtmlSelectOneMenuRenderer extends HtmlSelectRenderer {
	

	public void encodeEnd(FacesContext context, UIComponent component)
		throws IOException {
	
		renderSelectOneMenu(context, (HtmlSelectOneMenu) component);
	}
	
	protected void renderSelectOneMenu(FacesContext context, HtmlSelectOneMenu component)
			throws IOException {
		
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.SELECT_ELEM, component);
        RenderUtil.renderIdIfNecessary(writer, component, context);
        writer.writeAttribute(JsfConstants.NAME_ATTR, component
                .getClientId(context), null);

        List selectItemList = SelectItemUtil.getSelectItemList(component);
        String size = (String)component.getAttributes().get(JsfConstants.SIZE_ATTR);
        writer.writeAttribute(JsfConstants.SIZE_ATTR, null == size ? "1" : size, null);
        RenderUtil.renderAttributes(writer, component, JsfConstants.SELECT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if (component.isDisabled()) {
            writer.writeAttribute(JsfConstants.DISABLED_ATTR, Boolean.TRUE, null);
        }
        String valueStr = ValueHolderUtil.getValueAsString(context, component);
        List lookupAsString = new ArrayList();
        lookupAsString.add(valueStr);
        renderSelectOptions(context, component, selectItemList, lookupAsString);
        writer.writeText("", null);
        writer.endElement(JsfConstants.SELECT_ELEM);
    }
	
	public void decode(FacesContext context, UIComponent component) {
		DecodeUtil.decode(context, component);
	}

	public Object getConvertedValue(FacesContext context,
			UIComponent component, Object submittedValue)
			throws ConverterException {
		
		return RenderUtil.getConvertedUIOutputValue(context, (UIOutput) component, submittedValue);
	}
}
