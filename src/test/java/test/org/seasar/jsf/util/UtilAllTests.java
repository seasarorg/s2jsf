package test.org.seasar.jsf.util;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author higa
 *
 */
public class UtilAllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(UtilAllTests.class);
	}

	public static Test suite() {
		TestSuite suite =
			new TestSuite("Test for test.org.seasar.jsf.util");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(BindingUtilTest.class));
		//$JUnit-END$
		return suite;
	}
}
