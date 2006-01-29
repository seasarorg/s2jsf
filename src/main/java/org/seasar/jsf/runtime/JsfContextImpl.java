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
package org.seasar.jsf.runtime;

import javax.servlet.jsp.PageContext;

import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfContext;
import org.seasar.jsf.TagPool;

/**
 * @author higa
 *  
 */
public class JsfContextImpl implements JsfContext {

	private PageContext pageContext;

	private JsfConfig jsfConfig;

	private TagPool tagPool;

	public JsfContextImpl(PageContext pageContext, JsfConfig jsfConfig,
			TagPool tagPool) {

		this.pageContext = pageContext;
		this.jsfConfig = jsfConfig;
		this.tagPool = tagPool;
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	public JsfConfig getJsfConfig() {
		return jsfConfig;
	}

	public TagPool getTagPool() {
		return tagPool;
	}
}