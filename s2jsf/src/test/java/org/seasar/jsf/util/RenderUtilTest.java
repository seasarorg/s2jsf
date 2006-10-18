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
import org.seasar.jsf.JsfConstants;
import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 * @author yone
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

    public void testIsDefaultValue() throws Exception {
        assertEquals(true, RenderUtil.isDefaultAttributeValue(null));

        assertEquals(true, RenderUtil.isDefaultAttributeValue(Boolean.FALSE));
        assertEquals(true, RenderUtil.isDefaultAttributeValue(new Integer(
                Integer.MIN_VALUE)));
        assertEquals(true, RenderUtil.isDefaultAttributeValue(new Double(
                Double.MIN_VALUE)));
        assertEquals(true, RenderUtil.isDefaultAttributeValue(new Long(
                Long.MIN_VALUE)));
        assertEquals(true, RenderUtil.isDefaultAttributeValue(new Byte(
                Byte.MIN_VALUE)));
        assertEquals(true, RenderUtil.isDefaultAttributeValue(new Float(
                Float.MIN_VALUE)));
        assertEquals(true, RenderUtil.isDefaultAttributeValue(new Short(
                Short.MIN_VALUE)));

        assertEquals(false, RenderUtil.isDefaultAttributeValue(Boolean.TRUE));
        assertEquals(false, RenderUtil.isDefaultAttributeValue(new Integer(0)));
        assertEquals(false, RenderUtil.isDefaultAttributeValue(new Integer(-1)));
        assertEquals(false, RenderUtil.isDefaultAttributeValue(new Integer(1)));
        assertEquals(false, RenderUtil.isDefaultAttributeValue(new Integer(
                Integer.MAX_VALUE)));
        assertEquals(false, RenderUtil.isDefaultAttributeValue(new Double(1.0)));
        assertEquals(false, RenderUtil.isDefaultAttributeValue(new Long(100L)));
        assertEquals(false, RenderUtil.isDefaultAttributeValue(new Byte("1")));
        assertEquals(false, RenderUtil
                .isDefaultAttributeValue(new Float(1.23F)));
        assertEquals(false, RenderUtil.isDefaultAttributeValue(new Short("12")));

        assertEquals(false, RenderUtil.isDefaultAttributeValue(""));
    }

    
    public void testRenderAttribute_DisabledTrue() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("input", null);
        responseWriter.writeAttribute("type", "button", null);
        
        RenderUtil.renderAttribute(responseWriter, JsfConstants.DISABLED_ATTR, Boolean.TRUE, null);
        String value = writer.toString();
        assertEquals("<input type=\"button\" disabled=\"true\"", value);
    }
    
    public void testRenderAttribute_DisabledTrue2() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("input", null);
        responseWriter.writeAttribute("type", "button", null);
        
        RenderUtil.renderAttribute(responseWriter, JsfConstants.DISABLED_ATTR, JsfConstants.DISABLED_ATTR, null);
        String value = writer.toString();
        assertEquals("<input type=\"button\" disabled=\"disabled\"", value);
    }

    public void testRenderAttribute_DisabledFalse() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("input", null);
        responseWriter.writeAttribute("type", "button", null);
        
        RenderUtil.renderAttribute(responseWriter, JsfConstants.DISABLED_ATTR, Boolean.FALSE, null);
        String value = writer.toString();
        assertEquals("<input type=\"button\"", value);
    }
    
}
