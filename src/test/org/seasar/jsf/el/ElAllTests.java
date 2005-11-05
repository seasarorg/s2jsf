package test.org.seasar.jsf.el;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author higa
 *
 */
public class ElAllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ElAllTests.class);
	}

	public static Test suite() {
		TestSuite suite =
			new TestSuite("Test for test.org.seasar.jsf.el");
		//$JUnit-BEGIN$
		//$JUnit-END$
		return suite;
	}
}
