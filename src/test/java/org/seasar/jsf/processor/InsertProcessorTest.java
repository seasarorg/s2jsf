package org.seasar.jsf.processor;

import java.util.ArrayList;
import java.util.List;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.mock.MockApplication;
import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockValueBinding;

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
}
