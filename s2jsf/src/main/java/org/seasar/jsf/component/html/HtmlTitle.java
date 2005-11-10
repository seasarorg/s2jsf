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
package org.seasar.jsf.component.html;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author higa
 *  
 */
public class HtmlTitle extends UIComponentBase {

	public static final String COMPONENT_TYPE = "org.seasar.jsf.Title";

	public static final String COMPONENT_FAMILY = "javax.faces.Output";
	
	public static final String DEFAULT_RENDERER_TYPE = "org.seasar.jsf.Title";

	private Object value;

	public HtmlTitle() {
		setRendererType(DEFAULT_RENDERER_TYPE);
	}

	public Object getValue() {
		if (value != null) {
			return value;
		}
        ValueBinding vb = getValueBinding("value");
        return vb != null ? vb.getValue(getFacesContext()) : null;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	public Object saveState(FacesContext context) {
		Object[] values = new Object[2];
		values[0] = super.saveState(context);
		values[1] = value;
		return values;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		value = values[1];
	}
}