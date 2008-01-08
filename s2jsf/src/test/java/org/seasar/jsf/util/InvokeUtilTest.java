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
package org.seasar.jsf.util;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class InvokeUtilTest extends TeedaTestCase {

    public void testInvoke_IgnoreSimpleComponentDef() throws Exception {
        MockMethodBinding mb = new MockMethodBinding();
        Hoge hoge = new Hoge();
        Foo foo = new Foo();
        hoge.setRequest(getRequest());
        hoge.setFoo(foo);
        foo.setName("bbb");
        ComponentDef componentDef = new ComponentDefImpl(hoge.getClass(), "aaa");
        getContainer().register(componentDef);
        ComponentDef componentDef2 = new ComponentDefImpl(foo.getClass(), "foo");
        getContainer().register(componentDef2);
        mb.setExpressionString("#{aaa.bbb}");
        try {
            InvokeUtil.invoke(mb, getFacesContext());
            success();
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
    }

    public static class Hoge {

        private HttpServletRequest request;

        private Foo foo;

        public Foo getFoo() {
            return foo;
        }

        public void setFoo(Foo foo) {
            this.foo = foo;
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }

    }

    public static class Foo {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class MockMethodBinding extends MethodBinding {

        private String expressionString;

        public String getExpressionString() {
            return expressionString;
        }

        public void setExpressionString(String expressionString) {
            this.expressionString = expressionString;
        }

        public Class getType(FacesContext context)
                throws MethodNotFoundException {
            return null;
        }

        public Object invoke(FacesContext context, Object[] params) {
            return null;
        }

    }
}
