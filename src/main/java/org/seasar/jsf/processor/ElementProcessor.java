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

import org.seasar.framework.util.StringUtil;
import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfContext;
import org.seasar.jsf.TagPool;
import org.seasar.jsf.taglib.ElementTag;
import org.xml.sax.Attributes;

/**
 * @author higa
 * @author yone
 */
public class ElementProcessor extends TagProcessorImpl {

    private String tagName;

    private StringBuffer afterContents;

    public ElementProcessor() {
        setTagClass(ElementTag.class);
        // JIRA-65対応 passthroughを属性として保持する
        // addCustomPropertyName(JsfConstants.PASSTHROUGH_ATTR);
    }

    public void setup(String namespaceURI, String localName, String qName,
            Attributes attributes, JsfConfig jsfConfig) {

        tagName = qName;
        super.setup(namespaceURI, localName, qName, attributes, jsfConfig);
    }

    public String getTagName() {
        return tagName;
    }

    public void addAfterContents(String text) {
        if (text == null) {
            return;
        }
        if (afterContents == null) {
            afterContents = new StringBuffer(text);
        } else {
            afterContents.append(text);
        }
    }

    public void setProperties(Tag tag, JsfContext pagesContext) {
        ElementTag elementTag = (ElementTag) tag;
        elementTag.setTagName(tagName);
        if (afterContents != null) {
            elementTag.setAfterContents(afterContents.toString());
        }
        for (Iterator i = getPropertyKeys(); i.hasNext();) {
            String propertyName = (String) i.next();
            Object value = getProperty(propertyName);
            String s = (String) value;
            if (StringUtil.isEmpty(s) || isCustomProperty(propertyName)) {
                continue;
            }
            if (value != null) {
                elementTag.addAttribute(propertyName, value.toString());
            }
        }
    }

    public void process(JsfContext pagesContext, Tag parentTag)
            throws JspException {

        TagPool tagPool = pagesContext.getTagPool();
        Tag tag = tagPool.request(getTagClass());
        try {
            process(pagesContext, tag, parentTag);
        } finally {
            tag.release();
            tagPool.release(tag);
        }
    }
}