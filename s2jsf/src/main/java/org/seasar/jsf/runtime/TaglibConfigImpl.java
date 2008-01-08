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

import java.util.HashMap;
import java.util.Map;

import org.seasar.jsf.TagConfig;
import org.seasar.jsf.TaglibConfig;
import org.seasar.jsf.exception.TagNotFoundRuntimeException;

/**
 * @author higa
 *
 */
public class TaglibConfigImpl implements TaglibConfig {

	private String uri;

	private Map tagConfigs = new HashMap();
	
	public TaglibConfigImpl() {
	}

	/**
	 * @see org.seasar.maya.config.TaglibConfig#getUri()
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @see org.seasar.maya.config.TaglibConfig#setUri(java.lang.String)
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @see org.seasar.maya.config.TaglibConfig#getTagConfig(java.lang.String)
	 */
	public TagConfig getTagConfig(String name) {
		TagConfig tagConfig = (TagConfig) tagConfigs.get(name);
		if (tagConfig == null) {
			throw new TagNotFoundRuntimeException(name);
		}
		return tagConfig;
	}

	/**
	 * @see org.seasar.maya.config.TaglibConfig#addTagConfig(org.seasar.maya.config.TagConfig)
	 */
	public void addTagConfig(TagConfig tagConfig) {
		tagConfigs.put(tagConfig.getName(), tagConfig);
	}

}
