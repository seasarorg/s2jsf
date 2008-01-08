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

import java.util.Iterator;

import javax.servlet.jsp.tagext.Tag;

import org.seasar.framework.util.StringUtil;
import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.JsfContext;
import org.seasar.jsf.taglib.S2ValidatorTag;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class ValidatorProcessor extends TagProcessorImpl {

	public ValidatorProcessor(String inject) {
		super(inject);
	}
	
	public void setup(String namespaceURI, String localName, String qName,
            Attributes attributes, JsfConfig jsfConfig) {

        removeProperty(JsfConstants.BINDING_ATTR);
        super.setup(namespaceURI, localName, qName, attributes, jsfConfig);
    }
    
    public void setProperties(Tag tag, JsfContext pagesContext) {
        S2ValidatorTag validatorTag = (S2ValidatorTag) tag;
        for (Iterator i = getPropertyKeys(); i.hasNext();) {
            String propertyName = (String) i.next();
            Object value = getProperty(propertyName);
            String s = (String) value;
            if (StringUtil.isEmpty(s) || isCustomProperty(propertyName)) {
                continue;
            }
            if (value != null) {
                validatorTag.addAttribute(propertyName, s);
            }
        }
    }
}