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
package org.seasar.jsf.runtime;

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.TagProcessor;
import org.seasar.jsf.TagSelector;
import org.seasar.jsf.ViewTemplateFactory;
import org.seasar.jsf.processor.ElementProcessor;
import org.seasar.jsf.processor.ViewProcessor;
import org.seasar.jsf.util.HtmlNodeUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class TagProcessorHandler extends DefaultHandler {

    private TagSelectors tagSelectors;

    private JsfConfig jsfConfig;

    private Stack processorStack = new Stack();

    private TagProcessor root;

    public TagProcessorHandler(TagSelectors tagSelectors, JsfConfig jsfConfig,
            ViewTemplateFactory viewTemplateFactory) {

        this.tagSelectors = tagSelectors;
        this.jsfConfig = jsfConfig;
        root = new ViewProcessor(jsfConfig, viewTemplateFactory);
    }

    public TagProcessor getRoot() {
        return root;
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attributes) {

        TagSelector selector = tagSelectors.getTagSelector(namespaceURI,
                localName, qName, attributes);
        TagProcessor processor = null;
        if (selector != null) {
            processor = selector.createProcessor();
        }
        if (!processorStack.isEmpty()) {
            TagProcessor parentProcessor = peekProcessor();
            if (parentProcessor != null) {
                Map props = HtmlNodeUtil.convertMap(attributes);
                if (processor instanceof ElementProcessor
                        && !isElementNode(props)) {
                    parentProcessor.addText(HtmlNodeUtil.getStartTagString(
                            qName, props));
                    parentProcessor.incrementChildTextSize();
                    return;
                } else {
                    parentProcessor.addChild(processor);
                }
            }
        } else {
            root.addChild(processor);
        }
        processor.setup(namespaceURI, localName, qName, attributes, jsfConfig);
        processorStack.push(processor);
    }

    protected TagProcessor peekProcessor() {
        return (TagProcessor) processorStack.peek();
    }

    protected TagProcessor popProcessor() {
        return (TagProcessor) processorStack.pop();
    }

    protected boolean isElementNode(Map props) {
        for (Iterator i = props.values().iterator(); i.hasNext();) {
            String value = (String) i.next();
            if (value.indexOf("#{") >= 0) {
                return true;
            }
        }
        return false;
    }

    public void characters(char[] buffer, int start, int length) {
        TagProcessor processor = null;
        if (!processorStack.isEmpty()) {
            processor = peekProcessor();
            if (processor == null) {
                return;
            }
        } else {
            processor = root;
        }
        String text = new String(buffer, start, length);
        processor.addText(text);
    }

    public void endElement(String namespaceURI, String localName, String qName) {
        TagProcessor current = peekProcessor();
        if (current.getChildTextSize() == 0) {
            current.endElement();
            popProcessor();
        } else {
            current.addText(HtmlNodeUtil.getEndTagString(qName));
            current.decrementChildTextSize();
        }
    }

    public void error(SAXParseException e) throws SAXException {
        throw e;
    }

    public void warning(SAXParseException e) throws SAXException {
        System.err.println(e);
    }
}