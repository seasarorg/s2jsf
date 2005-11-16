/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.jsf.processor;

import org.seasar.framework.util.StringUtil;
import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.exception.TagProcessorNotFoundRuntimeException;
import org.xml.sax.Attributes;

/**
 * @author higa
 *  
 */
public class HtmlProcessor extends ElementProcessor {

	public HtmlProcessor() {
        addCustomPropertyName(JsfConstants.ACTION_ATTR);
	}
	
	public void setup(String namespaceURI, String localName, String qName,
			Attributes attributes, JsfConfig jsfConfig) {

		super.setup(namespaceURI, localName, qName, attributes, jsfConfig);
		ViewProcessor viewProcessor = (ViewProcessor) findAncestor(ViewProcessor.class);
		if (viewProcessor == null) {
			throw new TagProcessorNotFoundRuntimeException(ViewProcessor.class);
		}
		String extendsPath = getProperty(JsfConstants.EXTENDS_ATTR);
		if (!StringUtil.isEmpty(extendsPath)) {
			viewProcessor.setExtendsPath(extendsPath);
		}
		String initAction = getProperty(JsfConstants.ACTION_ATTR);
		if (!StringUtil.isEmpty(initAction)) {
			viewProcessor.setInitAction(initAction);
		}
	}
}