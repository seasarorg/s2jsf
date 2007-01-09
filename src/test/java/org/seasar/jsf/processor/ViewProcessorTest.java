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

import java.util.HashSet;
import java.util.Set;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.mock.MockApplication;
import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockValueBinding;
import org.seasar.jsf.processor.ViewProcessor;

/**
 * @author higa
 * 
 */
public class ViewProcessorTest extends S2TestCase {

    public ViewProcessorTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ViewProcessorTest.class);
    }

    public void testSetContentType() throws Exception {
        ViewProcessor processor = new ViewProcessor();
        processor.setContentType("text/html; charset=Windows-31j");
        assertEquals("1", "Windows-31j", processor.getEncoding());
    }

    public void testGetExtendsPath() throws Exception {
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
        context.setApplication(application);
        MockValueBinding vb = new MockValueBinding();
        application.setValueBinding(vb);
        context.setApplication(application);

        ViewProcessor processor = new ViewProcessor();
        vb.setValue(context, "hoge");
        processor.setExtendsPath("#{aaa}");
        assertEquals("hoge", processor.getExtendsPath());
    }

    public void testAddInsertProcessor() throws Exception {
        MockFacesContext context = new MockFacesContext();
        MockApplication application = new MockApplication();
        context.setApplication(application);
        MockValueBinding vb = new MockValueBinding();
        application.setValueBinding(vb);
        context.setApplication(application);

        ViewProcessor processor = new ViewProcessor();
        InsertProcessor ip = new InsertProcessor("insert");

        vb.setValue(context, new String[] { "/page1.html", "/page2.html" });
        ip.setProperty("src", "#{xxx}");
        processor.addInsertProcessor(ip);

        Set includes = new HashSet();
        processor.addIncludes(includes);

        assertTrue(includes.contains("/page1.html"));
        assertTrue(includes.contains("/page2.html"));
    }
}