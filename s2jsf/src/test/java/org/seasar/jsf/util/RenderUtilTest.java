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
package org.seasar.jsf.util;

import junit.framework.TestCase;

import org.seasar.framework.util.SPrintWriter;
import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 */
public class RenderUtilTest extends TestCase {

    public void testRenderAttribute() throws Exception {
        HtmlResponseWriter writer = new HtmlResponseWriter();
        writer.setWriter(new SPrintWriter());
        String attributeName = "rendered";
        Object value = "true";
        String propertyName = "hoge";
        writer.startElement("span", new MockUIComponent());
        assertTrue(RenderUtil.renderAttribute(writer, attributeName, value,
                propertyName));
        assertEquals("<span", writer.toString());
    }
}
