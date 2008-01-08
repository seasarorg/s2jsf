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

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.ForEach;

/**
 * @author higa
 *  
 */
public class ForEachTag extends UIComponentTagBase {

	private String items;
	
	private String var;
	
	private String varIndex;

	public ForEachTag() {
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
	
	public String getVarIndex() {
		return varIndex;
	}

	public void setVarIndex(String varIndex) {
		this.varIndex = varIndex;
	}

	public String getComponentType() {
		return ForEach.COMPONENT_TYPE;
	}

	public String getRendererType() {
		return null;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		setComponentProperty(component, JsfConstants.ITEMS_ATTR, items);
		ForEach forEach = (ForEach) component;
		forEach.setVar(var);
		forEach.setVarIndex(varIndex);
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release() {
		super.release();
		items = null;
		var = null;
		varIndex = null;
	}
}