package test.org.seasar.jsf.jsp;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author higa
 *
 */
public class JspAllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JspAllTests.class);
	}

	public static Test suite() {
		TestSuite suite =
			new TestSuite("Test for test.org.seasar.jsf.jsp");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(BodyContentImplTest.class));
		suite.addTest(new TestSuite(JspWriterImplTest.class));
		//$JUnit-END$
		return suite;
	}
}
