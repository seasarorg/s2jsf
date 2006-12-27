/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import junit.framework.TestCase;

import org.seasar.jsf.html.HtmlResponseWriter;
import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockHtmlInputSecret;

/**
 * @author yone
 */
public class HtmlInputSecretRendererTest extends TestCase {

    protected MockFacesContext mockFacesContext;

    private HtmlInputSecretRenderer renderer;

    private MockHtmlInputSecret htmlInputSecret;

    protected void setUp() throws Exception {
        super.setUp();
        mockFacesContext = new MockFacesContext();
        renderer = createHtmlInputSecretRenderer();
        htmlInputSecret = new MockHtmlInputSecret();
        htmlInputSecret.setId("testId");
        htmlInputSecret.setRenderer(renderer);
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Act ##
        encodeByRenderer(renderer, htmlInputSecret);

        // ## Assert ##
        assertEquals(
                "<input type=\"password\" id=\"testId\" name=\"testId\" value=\"\" />",
                getResponseText());
    }

    public void testEncodeEnd_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlInputSecret);
        renderer.encodeEnd(context, htmlInputSecret);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputSecret);

        // ## Assert ##
        assertEquals(
                "<input type=\"password\" id=\"testId\" name=\"testId\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithValueRedisplayTrue() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setValue("abc");
        htmlInputSecret.setRedisplay(true);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputSecret);

        // ## Assert ##
        assertEquals(
                "<input type=\"password\" id=\"testId\" name=\"testId\" value=\"abc\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute() throws Exception {
        htmlInputSecret.setId("a");
        htmlInputSecret.getAttributes().put("foo", "bar");

        encodeByRenderer(renderer, htmlInputSecret);

        assertEquals(
                "<input type=\"password\" id=\"a\" name=\"a\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlInputSecret.setAccesskey("a");
        htmlInputSecret.setAlt("b");
        htmlInputSecret.setDir("c");
        htmlInputSecret.setDisabled(true);
        htmlInputSecret.setLang("e");
        htmlInputSecret.setMaxlength(5);
        htmlInputSecret.setOnblur("g");
        htmlInputSecret.setOnchange("h");
        htmlInputSecret.setOnclick("i");
        htmlInputSecret.setOndblclick("j");
        htmlInputSecret.setOnfocus("k");
        htmlInputSecret.setOnkeydown("l");
        htmlInputSecret.setOnkeypress("m");
        htmlInputSecret.setOnkeyup("n");
        htmlInputSecret.setOnmousedown("o");
        htmlInputSecret.setOnmousemove("p");
        htmlInputSecret.setOnmouseout("q");
        htmlInputSecret.setOnmouseover("r");
        htmlInputSecret.setOnmouseup("s");
        htmlInputSecret.setOnselect("t");
        htmlInputSecret.setReadonly(true);
        htmlInputSecret.setSize(2);
        htmlInputSecret.setStyle("w");
        htmlInputSecret.setStyleClass("u");
        htmlInputSecret.setTabindex("x");
        htmlInputSecret.setTitle("y");

        htmlInputSecret.setId("Aa");
        htmlInputSecret.setValue("Ba");
        htmlInputSecret.setRedisplay(false);

        encodeByRenderer(renderer, htmlInputSecret);
        final String expect = "<input type=\"password\" id=\"Aa\" name=\"Aa\""
            + " value=\"\" alt=\"b\" maxlength=\"5\" readonly=\"true\""
            + " size=\"2\" ondblclick=\"j\" onmousedown=\"o\" onmouseup=\"s\""
            + " onmouseover=\"r\" onmousemove=\"p\" onmouseout=\"q\" onkeypress=\"m\""
            + " onkeydown=\"l\" onkeyup=\"n\" onclick=\"i\" dir=\"c\" lang=\"e\""
            + " title=\"y\" style=\"w\" class=\"u\" accesskey=\"a\" tabindex=\"x\""
            + " onfocus=\"k\" onblur=\"g\" onselect=\"t\" onchange=\"h\" disabled=\"disabled\" />";
        assertEquals(expect, getResponseText());

    }
    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputSecret);

        // ## Assert ##
        assertEquals(null, htmlInputSecret.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setClientId("key");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key",
                "12345");

        // ## Act ##
        renderer.decode(context, htmlInputSecret);

        // ## Assert ##
        assertEquals("12345", htmlInputSecret.getSubmittedValue());
    }

    protected String getResponseText() throws IOException {
        MockFacesContext context = getFacesContext();
        HtmlResponseWriter htmlResponseWriter = ((HtmlResponseWriter) context
                .getResponseWriter());
        return htmlResponseWriter.toString();
    }

    protected void encodeByRenderer(Renderer renderer, UIComponent component)
            throws IOException {
        encodeByRenderer(renderer, getFacesContext(), component);
    }

    protected void encodeByRenderer(Renderer renderer, FacesContext context,
            UIComponent component) throws IOException {
        renderer.encodeBegin(context, component);
        if (renderer.getRendersChildren()) {
            renderer.encodeChildren(context, component);
        }
        renderer.encodeEnd(context, component);
    }

    private HtmlInputSecretRenderer createHtmlInputSecretRenderer() {
        return (HtmlInputSecretRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlInputSecretRenderer renderer = new HtmlInputSecretRenderer();
        return renderer;
    }

    protected MockFacesContext getFacesContext() {
        return mockFacesContext;
    }

}