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
package org.seasar.jsf;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.xml.sax.Attributes;

/**
 * @author higa
 * 
 */
public interface TagProcessor {

    public static final String LAST_PROCESSED_COMPONENT_ATTR = TagProcessor.class
            .getName()
            + ".LAST_PROCESSED_COMPONENT";

    void process(JsfContext pagesContext, Tag parentTag) throws JspException;

    int getChildCount();

    TagProcessor getChild(int index);

    void addChild(TagProcessor processor);

    TagProcessor getParent();

    void setParent(TagProcessor parent);

    String getProperty(String name);

    void setProperty(String name, String value);

    Iterator getPropertyKeys();

    void setup(String namespaceURI, String localName, String qName,
            Attributes attributes, JsfConfig jsfConfig);

    void setProperties(Tag tag, JsfContext jsfContext);

    TagProcessor findAncestor(Class clazz);

    void endElement();

    void addText(String text);

    int getChildTextSize();

    void incrementChildTextSize();

    void decrementChildTextSize();
}