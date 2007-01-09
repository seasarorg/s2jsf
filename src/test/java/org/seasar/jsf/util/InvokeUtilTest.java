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
package org.seasar.jsf.util;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.jsf.mock.MockMethodBinding;

public class InvokeUtilTest extends S2FrameworkTestCase {

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
            InvokeUtil.invoke(mb, null);
            assertTrue(true);
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

}
