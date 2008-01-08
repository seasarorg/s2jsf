/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import javax.servlet.http.HttpServletRequest;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.html.HtmlBase;
import org.seasar.jsf.util.ExternalContextUtil;

/**
 * @author higa
 *
 */
public class HtmlBaseRenderer extends Renderer {

	/**
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
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
		writer.startElement(JsfConstants.BASE_ELEM, component);
		HtmlBase base = (HtmlBase) component;
		String href = base.getHref();
		if (href == null) {
			HttpServletRequest request = ExternalContextUtil.getRequest(context.getExternalContext());
			href = calculateHref(request);
		}
		writer.writeAttribute(JsfConstants.HREF_ATTR, href, null);
		String target = base.getTarget();
		if (target != null) {
			writer.writeAttribute(JsfConstants.TARGET_ATTR, target, null);
		}
		writer.endElement(JsfConstants.BASE_ELEM);
	}

	public String calculateHref(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		url.append(request.getScheme());
		url.append("://");
		url.append(request.getServerName());
		url.append(':');
		url.append(request.getServerPort());
		url.append(request.getRequestURI());
		return url.toString();
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
	}
}