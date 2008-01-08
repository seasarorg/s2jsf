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
package org.seasar.jsf.render.html;

import javax.faces.render.Renderer;

import junit.framework.TestCase;

import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockHtmlInputText;

/**
 * @author yone
 */
public class HtmlInputTextRendererTest extends TestCase {

    private HtmlInputTextRenderer renderer;

    private org.seasar.jsf.mock.MockHtmlInputText htmlInputText;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlInputTextRenderer();
        htmlInputText = new MockHtmlInputText();
        htmlInputText.setRenderer(renderer);
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputText.setClientId("testkey");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputText);

        // ## Assert ##
        assertEquals(null, htmlInputText.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputText.setClientId("arg1");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap()
                .put("arg1", "123");

        // ## Act ##
        renderer.decode(context, htmlInputText);

        // ## Assert ##
        assertEquals("123", htmlInputText.getSubmittedValue());
    }

    private HtmlInputTextRenderer createHtmlInputTextRenderer() {
        return (HtmlInputTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlInputTextRenderer renderer = new HtmlInputTextRenderer();
        return renderer;
    }

    protected MockFacesContext getFacesContext() {
        return new MockFacesContext();
    }

}