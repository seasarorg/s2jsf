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
package org.seasar.jsf.taglib;

import javax.faces.component.UIComponent;

import org.seasar.jsf.component.UIInsert;

/**
 * @author higa
 *  
 */
public class UIInsertTag extends UIComponentTagBase {

	private String name;
	
	private String src;

	public UIInsertTag() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getComponentType() {
		return UIInsert.COMPONENT_TYPE;
	}

	public String getRendererType() {
		return null;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		UIInsert insert = (UIInsert) component;
		insert.setName(name);
		insert.setSrc(src);
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release() {
		super.release();
		name = null;
		src = null;
	}
}