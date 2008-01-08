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
package org.seasar.jsf.selector;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.TagProcessor;
import org.seasar.jsf.processor.OutputTextProcessor;
import org.xml.sax.Attributes;

/**
 * @author higa
 *  
 */
public class OutputTextSelector extends AbstractTagSelector {

	private static final String TAG_NAME = "outputText";
	
	public OutputTextSelector() {
	}

	public boolean isSelectable(String namespaceURI, String localName,
			String qName, Attributes attributes) {

		if (!JsfConstants.SPAN_ELEM.equalsIgnoreCase(qName)) {
			return false;
		}
		return attributes.getValue(JsfConstants.MAYA_NSURI,
				JsfConstants.INJECT_ATTR) == null
				&& attributes.getValue(JsfConstants.MAYA_NSURI,
						JsfConstants.VALUE_ATTR) != null;
	}

	public TagProcessor createProcessor() {
		return new OutputTextProcessor(getInject());
	}
	
	public String getInject() {
		return getJsfHtmlInject(TAG_NAME);
	}
}