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
package org.seasar.jsf.selector;

import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.TagSelector;

/**
 * @author higa
 *
 */
public abstract class AbstractTagSelector implements TagSelector {

	private JsfConfig jsfConfig;

	public void setJsfConfig(JsfConfig jsfConfig) {
		this.jsfConfig = jsfConfig;
	}
	
	protected String getTaglibPrefix(String uri) {
		return jsfConfig.getTaglibPrefix(uri);
	}
	
	protected String getJsfHtmlPrefix() {
		return getTaglibPrefix(JsfConstants.JSF_HTML_URI);
	}
	
	protected String getJsfCorePrefix() {
		return getTaglibPrefix(JsfConstants.JSF_CORE_URI);
	}
	
	protected String getS2JsfPrefix() {
		return getTaglibPrefix(JsfConstants.S2JSF_URI);
	}
	
	protected String getJsfHtmlInject(String tagName) {
		return getJsfHtmlPrefix() + ":" + tagName;
	}
	
	protected String getJsfCoreInject(String tagName) {
		return getJsfCorePrefix() + ":" + tagName;
	}
	
	protected String getS2JsfInject(String tagName) {
		return getS2JsfPrefix() + ":" + tagName;
	}
}