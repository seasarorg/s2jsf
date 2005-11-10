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

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.html.HtmlLink;

/**
 * @author higa
 *
 */
public class HtmlLinkRenderer extends Renderer {

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
		writer.startElement(JsfConstants.LINK_ELEM, component);
		HtmlLink link = (HtmlLink) component;
		String href = link.getHref();
		if (href == null) {
			throw new EmptyRuntimeException("href");
		}
		if (!href.startsWith("/")) {
			href = "/" + href;
		}
		ExternalContext extContext = context.getExternalContext();
		String contextPath = extContext.getRequestContextPath();
		if (!"/".equals(contextPath)) {
			href = contextPath + href;
		}
		writer.writeAttribute(JsfConstants.HREF_ATTR, href, null);
		String rel = link.getRel();
		if (rel != null) {
			writer.writeAttribute(JsfConstants.REL_ATTR, rel, null);
		}
		String type = link.getType();
		if (type != null) {
			writer.writeAttribute(JsfConstants.TYPE_ATTR, type, null);
		}
		String media = link.getMedia();
		if (media != null) {
			writer.writeAttribute(JsfConstants.MEDIA_ATTR, media, null);
		}
		writer.endElement(JsfConstants.LINK_ELEM);
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