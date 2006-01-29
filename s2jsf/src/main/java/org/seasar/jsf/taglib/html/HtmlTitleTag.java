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
package org.seasar.jsf.taglib.html;

import javax.faces.component.UIComponent;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.html.HtmlTitle;
import org.seasar.jsf.taglib.UIComponentTagBase;

/**
 * @author higa
 *
 */
public class HtmlTitleTag extends UIComponentTagBase {

	private String value;
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getComponentType() {
		return HtmlTitle.COMPONENT_TYPE;
	}

	public String getRendererType() {
		return HtmlTitle.DEFAULT_RENDERER_TYPE;
	}
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		setComponentProperty(component, JsfConstants.VALUE_ATTR, value);
	}
	
	public void release() {
		super.release();
		value = null;
	}
}