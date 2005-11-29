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
package org.seasar.jsf.webapp;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;

import org.seasar.framework.util.SAXParserFactoryUtil;
import org.seasar.framework.xml.SaxHandler;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.framework.xml.TagHandlerRule;

/**
 * @author manhole
 */
public class WebappConfigBuilder {

    private static TagHandlerRule rule_ = new WebappTagHandlerRule();
    private static final String DTD_PATH = "javax/servlet/resources/web-app_2_3.dtd";
    private static final String PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN";

    public WebappConfig build(InputStream is) {
        SaxHandlerParser parser = createSaxHandlerParser();
        return (WebappConfig) parser.parse(is);
    }

    private SaxHandlerParser createSaxHandlerParser() {
        SAXParser saxParser = SAXParserFactoryUtil.newSAXParser();
        SaxHandler handler = new SaxHandler(rule_);
        handler.registerDtdPath(PUBLIC_ID, DTD_PATH);
        return new SaxHandlerParser(handler, saxParser);
    }

}
