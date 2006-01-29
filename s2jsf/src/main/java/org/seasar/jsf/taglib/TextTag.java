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
package org.seasar.jsf.taglib;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

import org.seasar.jsf.component.UIText;

/**
 * @author higa
 *  
 */
public class TextTag extends UIComponentTag {

	private String value;

	public TextTag() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComponentType() {
		return UIText.COMPONENT_TYPE;
	}

	public String getRendererType() {
		return UIText.DEFAULT_RENDERER_TYPE;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component) {
		UIText text = (UIText) component;
		text.setValue(value);
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release() {
		super.release();
		value = null;
	}
}