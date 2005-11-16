package org.seasar.jsf.runtime;

import java.io.InputStream;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.TagProcessorTreeFactory;
import org.seasar.jsf.jsp.PageContextImpl;
import org.seasar.jsf.processor.ViewProcessor;

/**
 * @author higa
 *  
 */
public class TagProcessorTreeFactoryImplTest extends S2TestCase {

	private static final String PATH = "org/seasar/jsf/runtime/hello.html";

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
}