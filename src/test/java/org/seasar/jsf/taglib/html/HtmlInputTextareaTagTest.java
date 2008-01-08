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
package org.seasar.jsf.taglib.html;

import javax.faces.component.UIComponent;

import junit.framework.TestCase;

import org.seasar.jsf.component.html.S2HtmlInputTextarea;

/**
 * @author yone
 */
public class HtmlInputTextareaTagTest extends TestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        HtmlInputTextareaTag tag = new HtmlInputTextareaTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlInputTextarea", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        HtmlInputTextareaTag tag = new HtmlInputTextareaTag();

        // # Act & Assert #
        assertEquals("javax.faces.Textarea", tag.getRendererType());
    }

    public void testSetProperties_Org() throws Exception {
        // # Arrange #
        S2HtmlInputTextarea component = createHtmlInputTextarea();
        HtmlInputTextareaTag tag = new HtmlInputTextareaTag() {
            // NullPointerException is caused in unit test by myfaces
            // UIComponentTag#setProperties
            // see myfaces-1.0.9 UIComponentTag#setProperties llne:626
            public String getRendererType() {
                return null;
            }
        };
        // tag.setAutocomplete("off");
        tag.setWrap("virtual");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertEquals("virtual", component.getWrap());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        S2HtmlInputTextarea component = createHtmlInputTextarea();
        HtmlInputTextareaTag tag = new HtmlInputTextareaTag() {
            public String getRendererType() {
                return null;
            }
        };
        tag.setWrap("off");
        tag.setProperties(component);

        // # Act #
        tag.release();

        // # Assert #
        super.assertNull(tag.getWrap());
    }

    private S2HtmlInputTextarea createHtmlInputTextarea() {
        return (S2HtmlInputTextarea) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new S2HtmlInputTextarea();
    }

}