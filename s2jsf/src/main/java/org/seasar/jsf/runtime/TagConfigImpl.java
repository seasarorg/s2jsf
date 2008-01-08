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
package org.seasar.jsf.runtime;

import org.seasar.jsf.TagConfig;

/**
 * @author higa
 *  
 */
public class TagConfigImpl implements TagConfig {

	private String name;

	private Class tagClass;

	public TagConfigImpl() {
	}

	/**
	 * @see org.seasar.maya.config.TagConfig#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see org.seasar.maya.config.TagConfig#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see org.seasar.maya.config.TagConfig#getTagClass()
	 */
	public Class getTagClass() {
		return tagClass;
	}

	/**
	 * @see org.seasar.maya.config.TagConfig#setTagClass(java.lang.Class)
	 */
	public void setTagClass(Class tagClass) {
		this.tagClass = tagClass;
	}
}