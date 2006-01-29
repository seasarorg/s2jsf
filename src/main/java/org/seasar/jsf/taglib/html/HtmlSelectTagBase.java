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
import org.seasar.jsf.component.S2UISelectItems;
import org.seasar.jsf.exception.NoValueReferenceRuntimeException;
import org.seasar.jsf.util.BindingUtil;

/**
 * @author higa
 *
 */
public abstract class HtmlSelectTagBase extends HtmlInputTagBase {
	
	private String border;
	
	private String disabledClass;

	private String enabledClass;
	
	private String items;
	
	private String itemLabel;
	
	private String itemValue;
	
	private String layout;
	
	private String nullLabel;

	public void setBorder(String border) {
		this.border = border;
	}
	
	public void setDisabledClass(String disabledClass) {
		this.disabledClass = disabledClass;
	}

	public void setEnabledClass(String enabledClass) {
		this.enabledClass = enabledClass;
	}
	
	public void setItems(String items) {
		this.items = items;
	}
	
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}
	
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	
	public void setLayout(String layout) {
		this.layout = layout;
	}
	
	public void setNullLabel(String nullLabel) {
		this.nullLabel = nullLabel;
	}
	
	public void release() {
		super.release();
		border = null;
		disabledClass = null;
		enabledClass = null;
		items = null;
		itemLabel = null;
		itemValue = null;
		layout = null;
		nullLabel = null;
	}
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		setComponentProperty(component, JsfConstants.BORDER_ATTR, border);
		setComponentProperty(component, JsfConstants.DISABLED_CLASS_ATTR,
				disabledClass);
		setComponentProperty(component, JsfConstants.ENABLED_CLASS_ATTR, enabledClass);
		setComponentProperty(component, JsfConstants.LAYOUT_ATTR, layout);
		if (items != null) {
			if (!isValueReference(items)) {
				throw new NoValueReferenceRuntimeException(items);
			}
			S2UISelectItems child = new S2UISelectItems();
			BindingUtil.setValueBinding(child, JsfConstants.VALUE_ATTR, items);
			if (itemValue != null) {
				child.setItemValue(itemValue);
			}
			if (itemLabel != null) {
				child.setItemLabel(itemLabel);
			}
			if (nullLabel != null) {
				child.setNullLabel(nullLabel);
			}
			component.getChildren().add(child);
		}
	}
}