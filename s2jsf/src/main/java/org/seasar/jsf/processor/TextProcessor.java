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

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfContext;
import org.seasar.jsf.TagPool;
import org.seasar.jsf.TagProcessor;
import org.seasar.jsf.taglib.TextTag;
import org.xml.sax.Attributes;

/**
 * @author higa
 *  
 */
public class TextProcessor implements TagProcessor {

	private String value;

	public TextProcessor() {
	}

	public TextProcessor(String value) {
		this.value = value;
	}
    
    public void addValue(String v) {
        this.value = this.value + v; 
    }
	
	public void setProperties(Tag tag, JsfContext jsfContext) {
		TextTag textTag = (TextTag) tag;
		textTag.setValue(value);
	}
	
	public Iterator getPropertyKeys() {
		return null;
	}

	public String getProperty(String name) {
		return null;
	}

	public void setProperty(String name, String value) {
	}
	
	public void setup(String namespaceURI, String localName,
			String qName, Attributes attributes, JsfConfig jsfConfig) {
	}

	public int getChildCount() {
		return 0;
	}

	public TagProcessor getChild(int index) {
		return null;
	}

	public void addChild(TagProcessor child) {
	}

	public TagProcessor getParent() {
		return null;
	}

	public void setParent(TagProcessor parent) {
	}

	public void process(JsfContext jsfContext, Tag parentTag)
			throws JspException {

		TagPool tagPool = jsfContext.getTagPool();
		Tag tag = tagPool.request(TextTag.class);
		try {
			tag.setPageContext(jsfContext.getPageContext());
			setProperties(tag, jsfContext);
			tag.doStartTag();
			tag.doEndTag();
		} finally {
			tag.release();
			tagPool.release(tag);
		}

	}

	public TagProcessor findAncestor(Class clazz) {
		return null;
	}
    
    public String getValue() {
        return this.value;
    }
    
}