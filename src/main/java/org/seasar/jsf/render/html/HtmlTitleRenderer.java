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
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.html.HtmlTitle;

/**
 * @author higa
 *
 */
public class HtmlTitleRenderer extends Renderer {

	public void encodeBegin(FacesContext facesContext, UIComponent component)
			throws IOException {

		if (!component.isRendered()) {
			return;
		}
	}

	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {

		if (!component.isRendered()) {
			return;
		}
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement(JsfConstants.TITLE_ELEM, component);
		HtmlTitle title = (HtmlTitle) component;
		Object value = title.getValue();
		if (value != null) {
			writer.write(value.toString());
		}
		writer.endElement(JsfConstants.TITLE_ELEM);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException {
	}
}