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
package org.seasar.jsf.component;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.StringUtil;

/**
 * @author higa
 *
 */
public class S2UISelectItem extends UISelectItem {

	public S2UISelectItem() {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return true;
	}

	/**
	 * @see javax.faces.component.UISelectItem#getItemLabel()
	 */
	public String getItemLabel() {
		String s = super.getItemLabel();
		if (!StringUtil.isEmpty(s)) {
			return s;
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < getChildCount(); ++i) {
			UIComponent child = (UIComponent) getChildren().get(i);
			if (child instanceof UIText) {
				String value = ((UIText) child).getValue();
				if (value != null) {
					value = value.trim();
					if (value.length() > 0) {
						buf.append(value);
					}
				}
			}
		}
		return buf.toString();
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
	}
}
