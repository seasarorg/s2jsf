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

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.render.Renderer;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.mock.MockApplication;
import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockHtmlCommandLink;
import org.seasar.jsf.mock.MockUIForm;
import org.seasar.jsf.mock.MockViewHandler;
import org.seasar.jsf.runtime.JsfConfigImpl;

/**
 * @author cero-t
 */
public class HtmlCommandLinkRendererTest extends S2TestCase {

    public void testEncodeBegin_AllowJavascript() throws Exception {
        // ## Arrange ##
        MockFacesContext context = new MockFacesContext();
        JsfConfig config = new JsfConfigImpl();
        config.setAllowJavascript(true);
        getContainer().register(config);
        Renderer renderer = new HtmlCommandLinkRenderer();
        HtmlCommandLink link = createHtmlCommandLink();

        // ## Act ##
        renderer.encodeBegin(context, link);
        renderer.encodeEnd(context, link);

        // ## Assert ##
        String expected = "<a href=\"#\" onclick=\"alert('test');"
                + "clear_form();document.forms['form'].elements['form:_link_hidden_'].value='link';"
                + "if(document.forms['form'].onsubmit){document.forms['form'].onsubmit();}"
                + "document.forms['form'].submit();return false;\" id=\"link\"></a>";
        assertEquals(expected, context.getResponseWriter().toString());
    }

    public void testEncodeBegin_NotAllowJavascript() throws Exception {
        // ## Arrange ##
        MockFacesContext context = new MockFacesContext();
        Application application = new MockApplication();
        MockViewHandler handler = new MockViewHandler();
        handler.setActionURL("http://localhost/test");
        application.setViewHandler(handler);
        context.setApplication(application);
        context.getViewRoot().setViewId("testpage");

        JsfConfig config = new JsfConfigImpl();
        config.setAllowJavascript(false);
        getContainer().register(config);
        Renderer renderer = new HtmlCommandLinkRenderer();
        HtmlCommandLink link = createHtmlCommandLink();

        // ## Act ##
        renderer.encodeBegin(context, link);
        renderer.encodeEnd(context, link);

        // ## Assert ##
        String expected = "<a href=\"http://localhost/test?form_testpage_SUBMIT=1&amp;form%3A_link_hidden_=link\" "
                + "onclick=\"alert('test')\" id=\"link\"></a>";
        assertEquals(expected, context.getResponseWriter().toString());
    }

    private HtmlCommandLink createHtmlCommandLink() {
        MockHtmlCommandLink link = new MockHtmlCommandLink();
        link.setClientId("link");
        MockUIForm form = new MockUIForm();
        form.setClientId("form");
        form.getChildren().add(link);
        Map attrs = link.getAttributes();
        attrs.put("onclick", "alert('test')");

        return link;
    }
}
