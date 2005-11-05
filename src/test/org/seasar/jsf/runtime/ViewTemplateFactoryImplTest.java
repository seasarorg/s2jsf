package test.org.seasar.jsf.runtime;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.ViewTemplate;
import org.seasar.jsf.ViewTemplateFactory;
import org.seasar.jsf.jsp.PageContextImpl;

/**
 * @author higa
 *
 */
public class ViewTemplateFactoryImplTest extends S2TestCase {

	private static final String PATH = "test/org/seasar/jsf/runtime/hello.html";
	private ViewTemplateFactory viewTemplateFactory;

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
}