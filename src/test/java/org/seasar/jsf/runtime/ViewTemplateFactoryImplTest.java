/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.jsf.runtime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.TagProcessorTreeFactory;
import org.seasar.jsf.ViewTemplate;
import org.seasar.jsf.ViewTemplateFactory;
import org.seasar.jsf.exception.PathNotFoundRuntimeException;
import org.seasar.jsf.jsp.PageContextImpl;

/**
 * @author higa
 * @author yone
 */
public class ViewTemplateFactoryImplTest extends S2TestCase {

    private static final String PATH = "org/seasar/jsf/runtime/hello.html";

    private ViewTemplateFactory viewTemplateFactory;

    private TagProcessorTreeFactory treeFactory;

    public ViewTemplateFactoryImplTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ViewTemplateFactoryImplTest.class);
    }

    protected void setUp() throws Exception {
        include("jsf.dicon");
    }

    public void testGetViewTemplate() throws Exception {
        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null);
        ViewTemplate template = viewTemplateFactory.getViewTemplate(PATH);
        assertNotNull("1", template);
        ViewTemplate template2 = viewTemplateFactory.getViewTemplate(PATH);
        assertSame("2", template, template2);
    }

    /*
     * htmlファイルが削除されたら、PathNotFoundRuntimeExceptionが投げられること。
     */
    public void testGetViewTemplate2() throws Exception {
        // ## Arrange ##
        final File from = ResourceUtil.getResourceAsFile(PATH);
        final File buildDir = ResourceUtil.getBuildDir(getClass());
        final File to = new File(buildDir, "aaa.html");
        copy(from, to);

        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null);

        // ## Act ##
        viewTemplateFactory.getViewTemplate("aaa.html");
        if (!to.delete()) {
            fail();
        }
        // ## Assert ##
        try {
            viewTemplateFactory.getViewTemplate("aaa.html");
            fail();
        } catch (final PathNotFoundRuntimeException e) {
        }
    }

    public void testGetViewTemplateFromResourceExist() throws Exception {
        // # Arrange #
        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null);
        ViewTemplateFactoryImpl viewTemplateFactoryImpl = new ViewTemplateFactoryImpl(
                treeFactory, getServletContext());

        // # Act #
        viewTemplateFactoryImpl.getViewTemplateFromResource(PATH);

        // # Assert #
        assertTrue(true);
    }

    public void testGetViewTemplateFromResourceNotExist() throws Exception {
        // # Arrange #
        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null);
        ViewTemplateFactoryImpl viewTemplateFactoryImpl = new ViewTemplateFactoryImpl(
                treeFactory, getServletContext());

        // # Act #
        try {
            viewTemplateFactoryImpl.getViewTemplateFromResource("hoge.html");
            fail();
        } catch (PathNotFoundRuntimeException e) {
            // # Assert #
            assertTrue(true);
        }
    }

    private void copy(final File from, final File to) throws IOException {
        BufferedInputStream is = null;
        BufferedOutputStream os = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        fis = new FileInputStream(from);
        is = new BufferedInputStream(fis);
        fos = new FileOutputStream(to);
        os = new BufferedOutputStream(fos);
        copy(is, os);
        os.close();
        is.close();
    }

    private void copy(final BufferedInputStream is,
            final BufferedOutputStream os) throws IOException {
        int n = 0;
        final byte[] buf = new byte[1024];
        while ((n = is.read(buf, 0, buf.length)) != -1) {
            os.write(buf, 0, n);
        }
    }
}