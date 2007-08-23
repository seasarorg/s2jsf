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

import javax.faces.context.FacesContext;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.UIElement;
import org.seasar.jsf.component.html.S2HtmlForm;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class HtmlElementRendererTest extends TeedaTestCase {

    /**
     * m:passthrough="true"の指定がない場合Test<br>
     * JIRA JSF-65(Seasar-user:8958)
     * 
     * @throws Exception
     */
    public void testPassthroughFalse() throws Exception {
        // ## Arrange ##
        HtmlElementRenderer renderer = new HtmlElementRenderer();

        S2HtmlForm form = new S2HtmlForm();
        form.setId("form1");
        UIElement elem = new UIElement();
        elem.setTagName("input");
        elem.setId("popWidth");
        elem.setIdSet(true);
        elem.getAttributes().put(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        elem.getAttributes().put(JsfConstants.VALUE_ATTR, "700");
        elem.setParent(form);

        // ## Act ##
        FacesContext context = getFacesContext();
        renderer.encodeBegin(context, elem);
        renderer.encodeEnd(context, elem);

        // ## Assert ##
        final String responseText = getResponse().getWriter().toString();
        assertTrue(responseText.indexOf("id=\"form1:popWidth\"") > 0);
        assertTrue(responseText.indexOf("value=\"700\"") > 0);
        assertTrue(responseText.indexOf("type=\"hidden\"") > 0);
    }

    /**
     * m:passthrough="true"を想定したTest<br>
     * JIRA JSF-65(Seasar-user:8958)
     * 
     * @throws Exception
     */
    public void testPassthroughTrue() throws Exception {
        // ## Arrange ##
        HtmlElementRenderer renderer = new HtmlElementRenderer();

        S2HtmlForm form = new S2HtmlForm();
        form.setId("form1");
        UIElement elem = new UIElement();
        elem.setTagName("input");
        elem.setId("popWidth");
        elem.setIdSet(true);
        elem.getAttributes().put(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        elem.getAttributes().put(JsfConstants.VALUE_ATTR, "700");
        elem.getAttributes().put(JsfConstants.PASSTHROUGH_ATTR, "true");

        elem.setParent(form);

        // ## Act ##
        FacesContext context = getFacesContext();
        renderer.encodeBegin(context, elem);
        renderer.encodeEnd(context, elem);

        // ## Assert ##
        final String responseText = getResponse().getWriter().toString();
        assertTrue(responseText.indexOf("id=\"popWidth\"") > 0);
        assertTrue(responseText.indexOf("value=\"700\"") > 0);
        assertTrue(responseText.indexOf("type=\"hidden\"") > 0);
    }

}
