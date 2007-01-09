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
package org.seasar.jsf.processor;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.JsfConfig;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class CommandButtonProcessor extends CommandProcessor {
	
	public CommandButtonProcessor(String inject) {
		super(inject);
	}

	public void setup(String namespaceURI, String localName,
			String qName, Attributes attributes, JsfConfig pagesConfig) {
		
		super.setup(namespaceURI, localName, qName, attributes, pagesConfig);
		setProperty(JsfConstants.TYPE_ATTR, JsfConstants.SUBMIT_VALUE);
	}
}