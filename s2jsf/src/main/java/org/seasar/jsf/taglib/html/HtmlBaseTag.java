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

import org.seasar.jsf.component.html.HtmlBase;
import org.seasar.jsf.taglib.UIComponentTagBase;

/**
 * @author higa
 *
 */
public class HtmlBaseTag extends UIComponentTagBase {

	private String href;
	
	private String target;
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getComponentType() {
		return HtmlBase.COMPONENT_TYPE;
	}

	public String getRendererType() {
		return HtmlBase.DEFAULT_RENDERER_TYPE;
	}
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		HtmlBase base = (HtmlBase) component;
		base.setHref(href);
		base.setTarget(target);
	}
	
	public void release() {
		super.release();
		href = null;
		target = null;
	}
}