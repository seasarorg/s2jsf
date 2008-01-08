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

import java.io.IOException;

import javax.faces.render.Renderer;

import junit.framework.TestCase;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.html.HtmlResponseWriter;
import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockUIElement;

/**
 * @author yone
 */
public class HtmlElementRendererTest extends TestCase {
    protected MockFacesContext context;

    private HtmlElementRenderer renderer;
    
    private MockUIElement elem;
    
    protected void setUp() throws Exception {
        super.setUp();
        context = new MockFacesContext();
        renderer = createHtmlElementRenderer();
        elem = new MockUIElement();
        elem.setRenderer(renderer);
    }
    
    /**
     * m:passthrough="true"の指定がない場合Test<br>
     * JIRA JSF-65(Seasar-user:8958)
     * 
     * @throws Exception
     */
    public void testPassthroughFalse() throws Exception {
        // ## Arrange ##
        elem.setTagName("input");
        elem.setId("popWidth");
        elem.setIdSet(true);
        elem.setClientId("form1:popWidth");
        elem.getAttributes().put(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        elem.getAttributes().put(JsfConstants.VALUE_ATTR, "700");

        // ## Act ##
        renderer.encodeBegin(context, elem);
        renderer.encodeEnd(context, elem);

        // ## Assert ##
        final String responseText = getResponseText();
        assertEquals(
                "<input id=\"form1:popWidth\" value=\"700\" type=\"hidden\" />",
                responseText);
    }

    /**
     * m:passthrough="true"を想定したTest<br>
     * JIRA JSF-65(Seasar-user:8958)
     * 
     * @throws Exception
     */
    public void testPassthroughTrue() throws Exception {
        // ## Arrange ##
        elem.setTagName("input");
        elem.setId("popWidth");
        elem.setIdSet(true);
        elem.getAttributes().put(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        elem.getAttributes().put(JsfConstants.VALUE_ATTR, "700");
        elem.getAttributes().put(JsfConstants.PASSTHROUGH_ATTR, "true");

        // ## Act ##
        renderer.encodeBegin(context, elem);
        renderer.encodeEnd(context, elem);

        // ## Assert ##
        final String responseText = getResponseText();
        assertEquals("<input id=\"popWidth\" value=\"700\" type=\"hidden\" />",
                responseText);
    }

    protected String getResponseText() throws IOException {
        MockFacesContext context = getFacesContext();
        HtmlResponseWriter htmlResponseWriter = ((HtmlResponseWriter) context
                .getResponseWriter());
        return htmlResponseWriter.toString();
    }

    protected MockFacesContext getFacesContext() {
        return context;
    }
    
    private HtmlElementRenderer createHtmlElementRenderer() {
        return (HtmlElementRenderer) createRenderer();
    }
    
    protected Renderer createRenderer() {
        HtmlElementRenderer renderer = new HtmlElementRenderer();
        return renderer;
    }


}
