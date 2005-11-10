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
package org.seasar.jsf.component;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * @author higa
 *  
 */
public class UIInsert extends UIComponentBase {

	public static final String COMPONENT_TYPE = "org.seasar.jsf.Insert";

	public static final String COMPONENT_FAMILY = "javax.faces.Output";

	public static final String DEFAULT_RENDERER_TYPE = null;

	private String src;
	
	private String name;
	
	public UIInsert() {
		setRendererType(DEFAULT_RENDERER_TYPE);
	}

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	public String getSrc() {
		return src;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Object saveState(FacesContext context) {
		Object[] values = new Object[3];
		values[0] = super.saveState(context);
		values[1] = src;
		values[2] = name;
		return values;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		src = (String) values[1];
		name = (String) values[2];
	}
}