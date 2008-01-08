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
package org.seasar.jsf.processor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.jsf.component.UIText;
import org.seasar.jsf.mock.MockFacesContext;

/**
 * @author cero-t
 */
public class TagProcessorImplTest extends S2TestCase {
    public TagProcessorImplTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TagProcessorImpl.class);
    }

    public void testIsPageModifiedNotExpression() {
        TagProcessorImpl tagProcessorImpl = new TagProcessorImpl();

        boolean result = tagProcessorImpl.isPageModified("xxx", "yyy");
        assertEquals(false, result);

        result = tagProcessorImpl.isPageModified("xxx", "yyy");
        assertEquals(false, result);
    }

    public void testIsPageModified() {
        FacesContext context = new MockFacesContext();
        TagProcessorImpl tagProcessorImpl = new TagProcessorImpl();
        boolean result = tagProcessorImpl.isPageModified("#{xxx}", "yyy");
        assertEquals(false, result);

        result = tagProcessorImpl.isPageModified("#{xxx}", "yyy");
        assertEquals(false, result);

        result = tagProcessorImpl.isPageModified("#{xxx}", "zzz");
        assertEquals(true, result);

        String nullString = null;
        result = tagProcessorImpl.isPageModified("#{xxx}", nullString);
        assertEquals(true, result);

        result = tagProcessorImpl.isPageModified("#{xxx}", nullString);
        assertEquals(false, result);

        result = tagProcessorImpl.isPageModified("#{xxx}", "xxx");
        assertEquals(true, result);

        ((MockHttpServletRequest) context.getExternalContext().getRequest())
                .setPathInfo("hoge");

        result = tagProcessorImpl.isPageModified("#{xxx}", "yyy");
        assertEquals(false, result);
    }

    public void testRestructComponentTree() {
        UIComponent component1 = new UIText();
        UIComponent component1_1 = new UIText();
        UIComponent component1_1_1 = new UIText();
        UIComponent component1_1_2 = new UIText();
        UIComponent component1_1_2_1 = new UIText();
        UIComponent component1_1_2_2 = new UIText();
        UIComponent component1_1_3 = new UIText();
        UIComponent component1_2 = new UIText();

        component1.getChildren().add(component1_1);
        component1_1.getChildren().add(component1_1_1);
        component1_1.getChildren().add(component1_1_2);
        component1_1_2.getChildren().add(component1_1_2_1);
        component1_1_2.getChildren().add(component1_1_2_2);
        component1_1.getChildren().add(component1_1_3);
        component1.getChildren().add(component1_2);

        FacesContext context = new MockFacesContext();
        context.getExternalContext().getRequestMap().put(
                TagProcessorImpl.LAST_PROCESSED_COMPONENT_ATTR,
                component1_1_2_1);

        TagProcessorImpl tagProcessorImpl = new TagProcessorImpl();
        tagProcessorImpl.restructComponentTree();

        assertEquals(1, component1.getChildCount());
        assertEquals(2, component1_1.getChildCount());
        assertEquals(1, component1_1_2.getChildCount());
    }
}
