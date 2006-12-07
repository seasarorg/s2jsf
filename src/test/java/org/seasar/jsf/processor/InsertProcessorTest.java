package org.seasar.jsf.processor;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.ErrorPageManager;
import org.seasar.jsf.JsfContext;
import org.seasar.jsf.TagProcessor;
import org.seasar.jsf.ViewTemplate;
import org.seasar.jsf.ViewTemplateFactory;
import org.seasar.jsf.mock.MockApplication;
import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockMethodBinding;
import org.seasar.jsf.mock.MockPageContext;
import org.seasar.jsf.mock.MockValueBinding;
import org.seasar.jsf.runtime.ErrorPageManagerImpl;
import org.seasar.jsf.runtime.JsfContextImpl;

/**
 * @author cero-t
 * 
 */
public class InsertProcessorTest extends S2TestCase {
    public InsertProcessorTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(InsertProcessorTest.class);
    }

    public void testGetSrcs() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");

        // Prepare for ValueBinding
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
        MockValueBinding vb = new MockValueBinding();
        application.setValueBinding(vb);
        context.setApplication(application);

        ip.setProperty("src", "/page.html");
        String[] srcs = ip.getSrcs();
        assertEquals("/page.html", srcs[0]);

        vb.setValue(null, "/page0.html");
        ip.setProperty("src", "#{xxx}");
        srcs = ip.getSrcs();
        assertEquals("/page0.html", srcs[0]);

        vb.setValue(null, new String[] { "/page1.html", "/page2.html" });
        ip.setProperty("src", "#{xxx}");
        srcs = ip.getSrcs();
        assertEquals("/page1.html", srcs[0]);
        assertEquals("/page2.html", srcs[1]);

        List list = new ArrayList();
        list.add("/page3.html");
        list.add("/page4.html");
        vb.setValue(null, list);
        ip.setProperty("src", "#{xxx}");
        srcs = ip.getSrcs();
        assertEquals("/page3.html", srcs[0]);
        assertEquals("/page4.html", srcs[1]);
    }

    public void testProcess1() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");

        // Prepare for MethodBinding
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
        MockMethodBindingInsertProcessor1 mb = new MockMethodBindingInsertProcessor1();
        application.setMethodBinding(mb);
        context.setApplication(application);

        // Prepare for processInclude
        getContainer().register(MockViewTemplateFactory.class);

        JsfContext jsfContext = new JsfContextImpl(new MockPageContext(), null,
                null);
        ip.setProperty("src", "test");
        ip.process(jsfContext, null);

        Object result = context.getExternalContext().getSessionMap().get(
                InsertProcessor.DYNAMIC_PAGE_ATTR + "-" + "/mock-path.jsp");
        assertEquals(result, null);
    }

    public void testProcess2() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");

        // Prepare for ValueBinding
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
        MockValueBinding vb = new MockValueBinding();
        application.setValueBinding(vb);
        context.setApplication(application);
        vb.setValue(null, null);

        JsfContext jsfContext = new JsfContextImpl(new MockPageContext(), null,
                null);
        ip.setProperty("src", "#{test}");
        ip.process(jsfContext, null);

        Object result = context.getExternalContext().getSessionMap().get(
                InsertProcessor.DYNAMIC_PAGE_ATTR + "-" + "/mock-path.jsp");
        assertEquals(result, Boolean.TRUE);
    }

    public void testProcessInclude1() throws Exception {
        InsertProcessor ip = new InsertProcessor("insert");

        // Prepare for MethodBinding
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
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
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
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
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
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
            return "test";
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
