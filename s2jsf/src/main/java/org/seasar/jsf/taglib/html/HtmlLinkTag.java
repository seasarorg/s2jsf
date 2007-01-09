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
package org.seasar.jsf.taglib.html;

import javax.faces.component.UIComponent;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.html.HtmlLink;
import org.seasar.jsf.taglib.UIComponentTagBase;

/**
 * @author higa
 *
 */
public class HtmlLinkTag extends UIComponentTagBase {

	private String rel;
	
	private String type;
	
	private String href;
	
	private String media;
	
	public void setRel(String rel) {
		this.rel = rel;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public void setMedia(String media) {
		this.media = media;
	}
	
	public String getComponentType() {
		return HtmlLink.COMPONENT_TYPE;
	}

	public String getRendererType() {
		return HtmlLink.DEFAULT_RENDERER_TYPE;
	}
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		setComponentProperty(component, JsfConstants.REL_ATTR, rel);
		setComponentProperty(component, JsfConstants.TYPE_ATTR, type);
		setComponentProperty(component, JsfConstants.HREF_ATTR, href);
		setComponentProperty(component, JsfConstants.MEDIA_ATTR, media);
	}
	
	public void release() {
		super.release();
		rel = null;
		type = null;
		href = null;
		media = null;
	}
}