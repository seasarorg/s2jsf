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
package org.seasar.jsf.component.html;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * @author higa
 *  
 */
public class HtmlLink extends UIComponentBase {

	public static final String COMPONENT_TYPE = "org.seasar.jsf.Link";

	public static final String COMPONENT_FAMILY = "javax.faces.Output";
	
	public static final String DEFAULT_RENDERER_TYPE = "org.seasar.jsf.Link";

	private String rel;
	
	private String type;

	private String href;
	
	private String media;

	public HtmlLink() {
		setRendererType(DEFAULT_RENDERER_TYPE);
	}
	
	public String getRel() {
		return this.rel;
	}
	
	public void setRel(String rel) {
		this.rel = rel;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public String getMedia() {
		return media;
	}
	
	public void setMedia(String media) {
		this.media = media;
	}

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	public Object saveState(FacesContext context) {
		Object[] values = new Object[5];
		values[0] = super.saveState(context);
		values[1] = rel;
		values[2] = type;
		values[3] = href;
		values[4] = media;
		return values;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		rel = (String) values[1];
		type = (String) values[2];
		href = (String) values[3];
		media = (String) values[4];
	}
}