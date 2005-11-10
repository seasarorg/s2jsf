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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.JsfContext;
import org.seasar.jsf.TagConfig;
import org.seasar.jsf.TagProcessor;
import org.xml.sax.Attributes;

/**
 * @author higa
 *  
 */
public class TagProcessorImpl implements TagProcessor {

	private static final String INSERT_PROCESSOR_MAP_ATTR = TagProcessorImpl.class
			.getName()
			+ ".INSERT_PROCESSOR_MAP";

	private Map properties = new HashMap();

	private Class tagClass;

	private List children = new ArrayList();

	private TagProcessor parent;

	private Set customPropertyNames = new HashSet();

	public TagProcessorImpl() {
	}

	public TagProcessorImpl(String inject) {
		setProperty(JsfConstants.INJECT_ATTR, inject);
	}

	public void setProperties(Tag tag, JsfContext jsfContext) {
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(tag.getClass());
		for (Iterator i = getPropertyKeys(); i.hasNext();) {
			String propertyName = (String) i.next();
			Object value = getProperty(propertyName);
			String s = (String) value;
			if (StringUtil.isEmpty(s) || isCustomProperty(propertyName)) {
				continue;
			}
			if (!beanDesc.hasPropertyDesc(propertyName)) {
				continue;
			}
			if (value != null) {
				PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
				pd.setValue(tag, value);
			}
		}
	}

	public String getProperty(String name) {
		return (String) properties.get(name);
	}

	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	public void removeProperty(String name) {
		properties.remove(name);
	}

	public Iterator getPropertyKeys() {
		return properties.keySet().iterator();
	}

	protected void addCustomPropertyName(String name) {
		customPropertyNames.add(name);
	}

	protected boolean isCustomProperty(String name) {
		return customPropertyNames.contains(name);
	}

	public void setup(String namespaceURI, String localName, String qName,
			Attributes attributes, JsfConfig jsfConfig) {

		setupProperties(attributes);
		renameProperties();
		processInject(jsfConfig);
	}

	protected void setupProperties(Attributes attributes) {
		if (attributes == null) {
			return;
		}
		int length = attributes.getLength();
		for (int i = 0; i < length; ++i) {
			if (JsfConstants.MAYA_NSURI.equals(attributes.getURI(i))) {
				properties.put(attributes.getLocalName(i), attributes
						.getValue(i));
			} else if (properties.get(attributes.getQName(i)) == null) {
				properties.put(attributes.getQName(i), attributes.getValue(i));
			}
		}
	}

	protected void renameProperties() {
		renameProperty(JsfConstants.CLASS_ATTR, JsfConstants.STYLE_CLASS_ATTR);
	}

	protected void renameProperty(String from, String to) {
		if (properties.containsKey(from)) {
			Object value = properties.remove(from);
			properties.put(to, value);
		}
	}

	protected void processInject(JsfConfig jsfConfig) {
		String inject = getProperty(JsfConstants.INJECT_ATTR);
		if (inject != null) {
			TagConfig tagConfig = jsfConfig.getTagConfig(inject);
			setTagClass(tagConfig.getTagClass());
		}
	}

	protected Class getTagClass() {
		return tagClass;
	}

	protected void setTagClass(Class tagClass) {
		this.tagClass = tagClass;
	}

	public int getChildCount() {
		return children.size();
	}

	public TagProcessor getChild(int index) {
		return (TagProcessor) children.get(index);
	}

	public void addChild(TagProcessor child) {
		children.add(child);
		child.setParent(this);
	}

	public TagProcessor getParent() {
		return parent;
	}

	public void setParent(TagProcessor parent) {
		this.parent = parent;
	}

	public void process(JsfContext jsfContext, Tag parentTag)
			throws JspException {

		if (tagClass == null) {
			return;
		}
		//TagPool tagPool = pagesContext.getTagPool();
		//Tag tag = tagPool.request(tagClass);
		Tag tag = (Tag) ClassUtil.newInstance(tagClass);
		try {
			process(jsfContext, tag, parentTag);
		} finally {
			tag.release();
			//tagPool.release(tag);
		}

	}

	protected void process(JsfContext jsfContext, Tag tag, Tag parentTag)
			throws JspException {

		if (parentTag != null) {
			tag.setParent(parentTag);
		}
		tag.setPageContext(jsfContext.getPageContext());
		setProperties(tag, jsfContext);
		if (tag instanceof BodyTag) {
			processBodyTag(jsfContext, (BodyTag) tag);
		} else if (tag instanceof IterationTag) {
			processIterationTag(jsfContext, (IterationTag) tag);
		} else {
			processTag(jsfContext, tag);
		}
	}

	protected void processTag(JsfContext pagesContext, Tag tag)
			throws JspException {

		if (Tag.SKIP_BODY != tag.doStartTag()) {
			processChildren(pagesContext, tag);
			tag.doEndTag();
		}
	}

	protected void processBodyTag(JsfContext jsfContext, BodyTag tag)
			throws JspException {

		int evalDoStartTag = tag.doStartTag();
		if (BodyTag.SKIP_BODY != evalDoStartTag) {
			PageContext pageContext = null;
			if (BodyTag.EVAL_BODY_INCLUDE != evalDoStartTag) {
				pageContext = jsfContext.getPageContext();
				BodyContent bodyContent = pageContext.pushBody();
				tag.setBodyContent(bodyContent);
				tag.doInitBody();
			}
			do {
				processChildren(jsfContext, tag);
			} while (IterationTag.EVAL_BODY_AGAIN == tag.doAfterBody());
			if (pageContext != null) {
				pageContext.popBody();
			}
		}
		tag.doEndTag();
	}

	protected void processIterationTag(JsfContext jsfContext, IterationTag tag)
			throws JspException {

		int evalDoStartTag = tag.doStartTag();
		if (BodyTag.SKIP_BODY != evalDoStartTag) {
			do {
				processChildren(jsfContext, tag);
			} while (IterationTag.EVAL_BODY_AGAIN == tag.doAfterBody());
		}
		tag.doEndTag();
	}

	protected void processChildren(JsfContext jsfContext, Tag parentTag)
			throws JspException {

		for (int i = 0; i < getChildCount(); i++) {
			TagProcessor child = getChild(i);
			child.process(jsfContext, parentTag);
		}
	}

	public TagProcessor findAncestor(Class clazz) {
		if (parent == null) {
			return null;
		}
		if (clazz.isInstance(parent)) {
			return parent;
		}
		return parent.findAncestor(clazz);
	}

	protected Map getInsertProcessorMap(PageContext pageContext) {
		Map map = (Map) pageContext.getAttribute(INSERT_PROCESSOR_MAP_ATTR);
		if (map == null) {
			map = new HashMap();
			pageContext.setAttribute(INSERT_PROCESSOR_MAP_ATTR, map);
		}
		return map;
	}
}