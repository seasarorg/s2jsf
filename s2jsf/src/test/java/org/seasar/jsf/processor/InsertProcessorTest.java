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
package org.seasar.jsf.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.ReferenceSyntaxException;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.jsf.ErrorPageManager;
import org.seasar.jsf.JsfContext;
import org.seasar.jsf.TagProcessor;
import org.seasar.jsf.ViewTemplate;
import org.seasar.jsf.ViewTemplateFactory;
import org.seasar.jsf.jsp.PageContextImpl;
import org.seasar.jsf.runtime.ErrorPageManagerImpl;
import org.seasar.jsf.runtime.JsfContextImpl;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author cero-t
 * 
 */
public class InsertProcessorTest extends TeedaTestCase {

    public InsertProcessorTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(InsertProcessorTest.class);
    }

    public void testGetSrcs() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");

        // Prepare for ValueBinding
        MockApplication application = getApplication();
        MockValueBinding vb = new MockValueBinding();
        application.setValueBinding(vb);

        ip.setProperty("src", "/page.html");
        String[] srcs = ip.getSrcs();
        assertEquals("/page.html", srcs[0]);

        vb.setValue(getFacesContext(), "/page0.html");
        ip.setProperty("src", "#{xxx}");
        srcs = ip.getSrcs();
        assertEquals("/page0.html", srcs[0]);

        vb.setValue(getFacesContext(), new String[] { "/page1.html",
                "/page2.html" });
        ip.setProperty("src", "#{xxx}");
        srcs = ip.getSrcs();
        assertEquals("/page1.html", srcs[0]);
        assertEquals("/page2.html", srcs[1]);

        List list = new ArrayList();
        list.add("/page3.html");
        list.add("/page4.html");
        vb.setValue(getFacesContext(), list);
        ip.setProperty("src", "#{xxx}");
        srcs = ip.getSrcs();
        assertEquals("/page3.html", srcs[0]);
        assertEquals("/page4.html", srcs[1]);
    }

    public void testProcess1() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");

        // Prepare for MethodBinding
        MockFacesContext context = getFacesContext();
        MockApplicationInsertProcessor application = new MockApplicationInsertProcessor();
        MockMethodBindingInsertProcessor1 mb = new MockMethodBindingInsertProcessor1();
        application.setMethodBinding(mb);
        context.setApplication(application);

        // Prepare for processInclude
        getContainer().register(MockViewTemplateFactory.class);

        JsfContext jsfContext = new JsfContextImpl(new PageContextImpl(), null,
                null);
        ip.setProperty("src", "test");
        ip.process(jsfContext, null);

        Map map = (Map) getExternalContext().getSessionMap().get(
                TagProcessorImpl.REFERENCE_VALUE_MAP_ATTR + "-"
                        + "/mock-path.jsp");
        assertNull(map);
    }

    public void testProcess2() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");
        // Prepare for MethodBinding
        MockFacesContext context = getFacesContext();
        MockApplicationInsertProcessor application = new MockApplicationInsertProcessor();
        MockMethodBindingInsertProcessor1 mb = new MockMethodBindingInsertProcessor1();
        application.setMethodBinding(mb);
        context.setApplication(application);

        // Prepare for ValueBinding
        MockValueBinding vb = new MockValueBinding();
        application.setValueBinding(vb);
        vb.setValue(null, "xxx");

        // Prepare for processInclude
        getContainer().register(MockViewTemplateFactory.class);

        JsfContext jsfContext = new JsfContextImpl(new PageContextImpl(), null,
                null);
        ip.setProperty("src", "#{test}");
        ip.process(jsfContext, null);

        Map map = (Map) getExternalContext().getSessionMap().get(
                TagProcessorImpl.REFERENCE_VALUE_MAP_ATTR + "-" + "/hello.jsp");
        Object[] result = (Object[]) map.get("#{test}");
        assertTrue(Arrays.equals(new String[] { "xxx" }, result));
    }

    public void testProcessInclude1() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");

        // Prepare for MethodBinding
        MockFacesContext context = getFacesContext();
        MockApplicationInsertProcessor application = new MockApplicationInsertProcessor();
        MockMethodBindingInsertProcessor1 mb = new MockMethodBindingInsertProcessor1();
        application.setMethodBinding(mb);
        context.setApplication(application);

        // Prepare for processInclude
        getContainer().register(MockViewTemplateFactory.class);

        ip.processInclude(null, null, null);
        assertTrue(mb.getIsCalled());
    }

    public void testProcessInclude2() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");

        // Prepare for MethodBinding
        MockFacesContext context = getFacesContext();
        MockApplicationInsertProcessor application = new MockApplicationInsertProcessor();
        MockMethodBindingInsertProcessor2 mb = new MockMethodBindingInsertProcessor2();
        application.setMethodBinding(mb);
        context.setApplication(application);

        // Prepare for processInclude
        getContainer().register(MockViewTemplateFactory.class);
        getContainer().register(ErrorPageManagerImpl.class);

        try {
            ip.processInclude(null, null, null);
            fail();
        } catch (EvaluationException e) {
            assertTrue(mb.getIsCalled());
        }
    }

    public void testProcessInclude3() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");

        // Prepare for MethodBinding
        MockFacesContext context = getFacesContext();
        MockApplicationInsertProcessor application = new MockApplicationInsertProcessor();
        MockMethodBindingInsertProcessor2 mb = new MockMethodBindingInsertProcessor2();
        application.setMethodBinding(mb);
        context.setApplication(application);

        // Prepare for processInclude
        getContainer().register(MockViewTemplateFactory.class);
        ErrorPageManager errorPageManager = new ErrorPageManagerImpl();
        errorPageManager.addErrorPart(NullPointerException.class, "");
        getContainer().register(errorPageManager);

        ip.processInclude(null, null, null);
        assertTrue(mb.getIsCalled());
    }

    public class MockMethodBindingInsertProcessor1 extends MockMethodBinding {
        private boolean isCalled = false;

        public Object invoke(FacesContext context, Object[] params) {
            isCalled = true;
            return null;
        }

        public boolean getIsCalled() {
            return isCalled;
        }
    }

    public class MockMethodBindingInsertProcessor2 extends MockMethodBinding {
        private boolean isCalled = false;

        public Object invoke(FacesContext context, Object[] params) {
            if (isCalled == true) {
                return null;
            }

            isCalled = true;
            throw new EvaluationException(new NullPointerException());
        }

        public boolean getIsCalled() {
            return isCalled;
        }
    }

    public static class MockApplicationInsertProcessor extends
            MockApplicationImpl {
        private MethodBinding mb;

        public void setMethodBinding(MethodBinding mb) {
            this.mb = mb;
        }

        public MethodBinding createMethodBinding(String ref, Class[] params)
                throws ReferenceSyntaxException {
            return this.mb;
        }
    }

    public static class MockViewTemplateFactory implements ViewTemplateFactory {
        public ViewTemplate getViewTemplate(String path) {
            return new MockViewTemplate();
        }
    }

    public static class MockViewTemplate implements ViewTemplate {
        public long getLastModified() {
            return 0;
        }

        public TagProcessor getRootTagProcessor() {
            return new MockViewProcessor();
        }

        public boolean isModified() {
            return false;
        }
    }

    public static class MockViewProcessor extends ViewProcessor {
        public InsertProcessor getInsertProcessor(String src) {
            return new MockInsertProcessor(null);
        }

        public String getInitAction() {
            return "";
        }
    }

    public static class MockInsertProcessor extends InsertProcessor {
        public MockInsertProcessor(String inject) {
            super(inject);
        }

        public void process(JsfContext jsfContext, Tag parentTag) {
            return;
        }
    }
}
