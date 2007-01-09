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
package org.seasar.jsf.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.component.html.S2HtmlInputText;
import org.seasar.jsf.mock.MockApplication;
import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockPageContext;
import org.seasar.jsf.taglib.html.HtmlInputTextTag;
import org.seasar.jsf.validator.S2LengthValidator;

/**
 * @author yone
 */
public class S2ValidatorExtTagTest extends S2TestCase {

    protected void setUp() throws Exception {
        include("s2validatorExt.dicon");
    }

    public void testDoStartTag_NotSetParent() throws Exception {
        // # Arrange #
        MockPageContext pageContext = new MockPageContext();
        pageContext.setRequest(getRequest());
        pageContext.setResponse(getResponse());
        S2ValidatorExtTag tag = new S2ValidatorExtTag();
        tag.setPageContext(pageContext);
        tag.setParent(null);
        try {
            // # Act #
            tag.doStartTag();
            fail();
        } catch (JspException e) {
            // # Assert #
            assertEquals("Not nested in a UIComponentTag", e.getMessage());
        }
    }

    public void testDoStartTag_returnEVAL_PAGE() throws Exception {
        // # Arrange #
        MockPageContext pageContext = new MockPageContext();
        pageContext.setRequest(getRequest());
        pageContext.setResponse(getResponse());
        S2ValidatorExtTag tag = new S2ValidatorExtTag();
        org.apache.myfaces.taglib.core.ViewTag view = new org.apache.myfaces.taglib.core.ViewTag();
        UIComponentTag textTag = new HtmlInputTextTag();
        List list = new ArrayList();
        list.add(view);
        list.add(textTag);
        setComponentStackAttribute(pageContext, list);
        tag.setPageContext(pageContext);

        // # Act #
        int ret = tag.doStartTag();

        // # Assert #
        assertEquals(ret, BodyTag.EVAL_PAGE);
    }

    public void testDoStartTag_createValidator() throws Exception {
        // # Arrange #
        MockPageContext pageContext = new MockPageContext();
        pageContext.setRequest(getRequest());
        pageContext.setResponse(getResponse());
        S2ValidatorExtTag tag = new S2ValidatorExtTag();
        tag.setValidatorId("testValidator");
        org.apache.myfaces.taglib.core.ViewTag view = new org.apache.myfaces.taglib.core.ViewTag();
        List list = new ArrayList();
        list.add(view);
        HtmlInputTextTag textTag = new HtmlInputTextTag() {
            public boolean getCreated() {
                return true;
            }

            public UIComponent getComponentInstance() {
                return new S2HtmlInputText();
            }
        };
        list.add(textTag);
        setComponentStackAttribute(pageContext, list);
        tag.setPageContext(pageContext);

        // # Act #
        int ret = tag.doStartTag();

        // # Assert #
        assertEquals(ret, BodyTag.EVAL_BODY_BUFFERED);
        Validator validator = (Validator) pageContext
                .getAttribute(JsfConstants.VALIDATOR_STACK_ATTR);
        assertNotNull(validator);
        assertTrue(validator instanceof S2LengthValidator);
    }

    public void testDoEndTag_returnEVAL_PAGE() throws Exception {
        // # Arrange #
        MockPageContext pageContext = new MockPageContext();
        pageContext.setRequest(getRequest());
        pageContext.setResponse(getResponse());
        S2ValidatorExtTag tag = new S2ValidatorExtTag();
        org.apache.myfaces.taglib.core.ViewTag view = new org.apache.myfaces.taglib.core.ViewTag();
        List list = new ArrayList();
        list.add(view);
        HtmlInputTextTag textTag = new HtmlInputTextTag();
        list.add(textTag);
        setComponentStackAttribute(pageContext, list);
        tag.setPageContext(pageContext);

        // # Act #
        int ret = tag.doEndTag();

        // # Assert #
        assertEquals(ret, BodyTag.EVAL_PAGE);
    }

    public void testDoEndTag_removeValidator() throws Exception {
        // # Arrange #
        MockPageContext pageContext = new MockPageContext();
        pageContext.setRequest(getRequest());
        pageContext.setResponse(getResponse());
        S2ValidatorExtTag tag = new S2ValidatorExtTag();
        tag.setValidatorId("testValidator");
        org.apache.myfaces.taglib.core.ViewTag view = new org.apache.myfaces.taglib.core.ViewTag();
        List list = new ArrayList();
        list.add(view);
        HtmlInputTextTag textTag = new HtmlInputTextTag() {
            public boolean getCreated() {
                return true;
            }

            public UIComponent getComponentInstance() {
                S2HtmlInputText s2text = new S2HtmlInputText();
                return s2text;
            }
        };
        list.add(textTag);
        setComponentStackAttribute(pageContext, list);
        tag.setPageContext(pageContext);

        // # Act #
        tag.doStartTag();
        int ret = tag.doEndTag();

        // # Assert #
        assertEquals(ret, BodyTag.EVAL_PAGE);
        Validator validator = (Validator) pageContext
                .getAttribute(JsfConstants.VALIDATOR_STACK_ATTR);
        assertNull(validator);
    }

    public void testCreateValidator_dicon1() throws Exception {
        S2ValidatorExtTag tag = new S2ValidatorExtTag();
        tag.setValidatorId("testValidator");
        Validator validator = tag.createValidator();
        assertNotNull(validator);
        assertTrue(validator instanceof S2LengthValidator);
    }

    public void testCreateValidator_dicon2() throws Exception {
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
        context.setApplication(application);
        S2ValidatorExtTag tag = new S2ValidatorExtTag();
        tag.setValidatorId("hogehoge");
        Validator validator = tag.createValidator();
        assertNull(validator);
    }

    public void testCreateValidator_facesConfig1() throws Exception {
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
        context.setApplication(application);
        application.addValidator("hoge",
                "org.seasar.jsf.validator.S2LengthValidator");
        S2ValidatorExtTag tag = new S2ValidatorExtTag();
        tag.setValidatorId("hoge");
        Validator validator = tag.createValidator();
        assertNotNull(validator);
        assertTrue(validator instanceof S2LengthValidator);
    }

    public void testCreateConverter_facesConfig2() throws Exception {
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
        context.setApplication(application);
        S2ValidatorExtTag tag = new S2ValidatorExtTag();
        tag.setValidatorId("hoge");
        Validator validator = tag.createValidator();
        assertNull(validator);
    }

    public static void setComponentStackAttribute(PageContext pageContext,
            List list) {
        pageContext.setAttribute(
                "javax.faces.webapp.UIComponentTag.COMPONENT_STACK", list,
                PageContext.REQUEST_SCOPE);
    }
}