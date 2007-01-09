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

import java.io.InputStream;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.TagConfig;
import org.seasar.jsf.TaglibConfig;

/**
 * @author higa
 * 
 */
public class TaglibConfigBuilderTest extends S2TestCase {

    private static final String PATH = "org/seasar/jsf/runtime/TaglibConfigBuilderTest.tld";

    public void testBuild() throws Exception {
        TaglibConfigBuilder builder = new TaglibConfigBuilder();
        InputStream is = ResourceUtil.getResourceAsStream(PATH);
        TaglibConfig taglibConfig = builder.build(is);
        assertNotNull("1", taglibConfig);
        assertEquals("2", "http://example.org/example", taglibConfig.getUri());
        TagConfig tagConfig = taglibConfig.getTagConfig("foo");
        assertEquals("3", FooTag.class, tagConfig.getTagClass());
    }

    public static class FooTag implements Tag {

        public void setPageContext(PageContext pageContext) {
        }

        public void setParent(Tag tag) {
        }

        public Tag getParent() {
            return null;
        }

        public int doStartTag() throws JspException {
            return SKIP_BODY;
        }

        public int doEndTag() throws JspException {
            return SKIP_BODY;
        }

        public void release() {
        }
    }

}
