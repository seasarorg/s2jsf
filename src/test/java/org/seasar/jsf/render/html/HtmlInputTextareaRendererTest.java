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
package org.seasar.jsf.render.html;

import javax.faces.render.Renderer;

import junit.framework.TestCase;

import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockHtmlInputTextarea;

/**
 * @author yone
 */
public class HtmlInputTextareaRendererTest extends TestCase {

    private HtmlInputTextareaRenderer renderer;

    private MockHtmlInputTextarea htmlInputTextarea;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlInputTextareaRenderer();
        htmlInputTextarea = new MockHtmlInputTextarea();
        htmlInputTextarea.setRenderer(renderer);
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputTextarea.setClientId("testkey");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputTextarea);

        // ## Assert ##
        assertEquals(null, htmlInputTextarea.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputTextarea.setClientId("arg1");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap()
                .put("arg1", "123");

        // ## Act ##
        renderer.decode(context, htmlInputTextarea);

        // ## Assert ##
        assertEquals("123", htmlInputTextarea.getSubmittedValue());
    }

    private HtmlInputTextareaRenderer createHtmlInputTextareaRenderer() {
        return (HtmlInputTextareaRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlInputTextareaRenderer renderer = new HtmlInputTextareaRenderer();
        return renderer;
    }

    protected MockFacesContext getFacesContext() {
        return new MockFacesContext();
    }

}