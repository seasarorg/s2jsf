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

import java.io.InputStream;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.TagProcessorTreeFactory;
import org.seasar.jsf.jsp.PageContextImpl;
import org.seasar.jsf.processor.TextProcessor;
import org.seasar.jsf.processor.ViewProcessor;

/**
 * @author higa
 *  
 */
public class TagProcessorTreeFactoryImplTest extends S2TestCase {

	private static final String PATH = "org/seasar/jsf/runtime/hello.html";

    private static final String PATH2 = "org/seasar/jsf/runtime/hello2.html";

    private static final String PATH3 = "org/seasar/jsf/runtime/hello3.html";

    private static final String PATH4 = "org/seasar/jsf/runtime/hello4.html";

	private TagProcessorTreeFactory treeFactory;

	public TagProcessorTreeFactoryImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TagProcessorTreeFactoryImplTest.class);
	}

	protected void setUp() throws Exception {
		include("jsf.dicon");
	}

	public void testCreateTagProcessorTree() throws Exception {
		PageContextImpl pageContext = new PageContextImpl();
		pageContext.initialize(getServlet(), getRequest(), getResponse(), null);
		InputStream is = ResourceUtil.getResourceAsStream(PATH);
		ViewProcessor viewProcessor = (ViewProcessor) treeFactory.createTagProcessorTree(is);
		assertNotNull("1", viewProcessor);
	}

    // test
    public void testXmlDecl_NoXmlDecl() throws Exception {
        // ## Arrange ##
        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null);
        InputStream is = ResourceUtil.getResourceAsStream(PATH);

        // ## Act ##
        ViewProcessor viewProcessor = (ViewProcessor) treeFactory
                .createTagProcessorTree(is);
        TextProcessor txt = (TextProcessor) viewProcessor.getChild(0);

        // ## Assert ##
        assertEquals(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n",
                txt.getValue());
    }

    // test <?xml version="1.0"?>
    public void testXmlDecl_SetVersion() throws Exception {
        // ## Arrange ##
        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null);
        InputStream is = ResourceUtil.getResourceAsStream(PATH2);

        // ## Act ##
        ViewProcessor viewProcessor = (ViewProcessor) treeFactory
                .createTagProcessorTree(is);
        TextProcessor txt = (TextProcessor) viewProcessor.getChild(0);

        // ## Assert ##
        assertEquals("<?xml version=\"1.0\"?>\n", txt.getValue());
    }

    // test <?xml version="1.0" encoding="UTF-8"?>
    public void testXmlDecl_SetVersionAndEncoding() throws Exception {
        // ## Arrange ##
        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null);
        InputStream is = ResourceUtil.getResourceAsStream(PATH3);

        // ## Act ##
        ViewProcessor viewProcessor = (ViewProcessor) treeFactory
                .createTagProcessorTree(is);
        TextProcessor txt = (TextProcessor) viewProcessor.getChild(0);

        // ## Assert ##
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n", txt
                .getValue());
    }

    // test <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    public void testXmlDecl_SetVersionAndEncodingAndStandalone()
            throws Exception {
        // ## Arrange ##
        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null);
        InputStream is = ResourceUtil.getResourceAsStream(PATH4);

        // ## Act ##
        ViewProcessor viewProcessor = (ViewProcessor) treeFactory
                .createTagProcessorTree(is);
        TextProcessor txt = (TextProcessor) viewProcessor.getChild(0);

        // ## Assert ##
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n",
                txt.getValue());
    }
    
}