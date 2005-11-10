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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.ValueHolderUtil;

/**
 * @author higa
 *
 */
public class HtmlOutputLinkRenderer extends Renderer {

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {

		renderOutputLinkStart(context, (UIOutput) component);
	}

	protected void renderOutputLinkStart(FacesContext context, UIOutput output)
			throws IOException {

		ResponseWriter writer = context.getResponseWriter();
		String href = ValueHolderUtil.getValueAsString(context, output);
		if (output.getChildCount() > 0) {
			StringBuffer hrefBuf = new StringBuffer(href);
			addParametersToHref(output, hrefBuf, (href.indexOf('?') == -1),
					writer.getCharacterEncoding());
			href = hrefBuf.toString();
		}
		href = context.getExternalContext().encodeResourceURL(href);
		writer.startElement(JsfConstants.ANCHOR_ELEM, output);
		writer.writeAttribute(JsfConstants.ID_ATTR,
				output.getClientId(context), null);
		writer.writeURIAttribute(JsfConstants.HREF_ATTR, href, null);
		RenderUtil.renderAttributes(writer, output,
				JsfConstants.ANCHOR_PASSTHROUGH_ATTRIBUTES);
		writer.flush();
	}

	protected void addParametersToHref(UIComponent component,
			StringBuffer hrefBuf, boolean firstParameter, String encoding)
			throws IOException {

		for (Iterator i = component.getChildren().iterator(); i.hasNext();) {
			UIComponent child = (UIComponent) i.next();
			if (child instanceof UIParameter) {
				String name = ((UIParameter) child).getName();
				Object value = ((UIParameter) child).getValue();
				addParameterToHref(name, value, hrefBuf, firstParameter,
						encoding);
				firstParameter = false;
			}
		}
	}

	protected void addParameterToHref(String name, Object value,
			StringBuffer hrefBuf, boolean firstParameter, String encoding)
			throws UnsupportedEncodingException {

		if (name == null) {
			throw new EmptyRuntimeException("name");
		}
		hrefBuf.append(firstParameter ? '?' : '&');
		hrefBuf.append(URLEncoder.encode(name, encoding));
		hrefBuf.append('=');
		if (value != null) {
			hrefBuf.append(URLEncoder.encode(value.toString(), encoding));
		}
	}

	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException {

		RenderUtil.encodeChildren(context, component);
	}

	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {

		renderLinkEnd(context, component);
	}

	protected void renderLinkEnd(FacesContext context, UIComponent component)
			throws IOException {

		ResponseWriter writer = context.getResponseWriter();
		writer.writeText("", null);
		writer.endElement(JsfConstants.ANCHOR_ELEM);
	}

}