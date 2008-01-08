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
package org.seasar.jsf.processor;

import org.seasar.framework.util.StringUtil;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.JsfConfig;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class CommandProcessor extends TagProcessorImpl {

	private static final String LOCATION_HREF = "location.href";
	
	public CommandProcessor() {
	}
	
	public CommandProcessor(String inject) {
		super(inject);
	}
	
	public void setup(String namespaceURI, String localName,
			String qName, Attributes attributes, JsfConfig jsfConfig) {
		
		super.setup(namespaceURI, localName, qName, attributes, jsfConfig);
		String onclick = getProperty(JsfConstants.ONCLICK_ATTR);
		if (!StringUtil.isEmpty(onclick) && onclick.length() > LOCATION_HREF.length()) {
			String s = onclick.substring(0, LOCATION_HREF.length());
			if (LOCATION_HREF.equalsIgnoreCase(s)) {
				int index = onclick.indexOf(JsfConstants.JS_STMT_END);
				if (index > 0) {
					onclick = onclick.substring(index + 1);
					setProperty(JsfConstants.ONCLICK_ATTR, onclick);
				} else {
					removeProperty(JsfConstants.ONCLICK_ATTR);
				}
			}
		}
	}
}