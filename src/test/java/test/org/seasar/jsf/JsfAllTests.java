package test.org.seasar.jsf;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.org.seasar.jsf.el.ElAllTests;
import test.org.seasar.jsf.exception.ExceptionAllTests;
import test.org.seasar.jsf.jsp.JspAllTests;
import test.org.seasar.jsf.processor.ProcessorAllTests;
import test.org.seasar.jsf.runtime.RuntimeAllTests;
import test.org.seasar.jsf.util.UtilAllTests;

/**
 * @author higa
 *
 */
public class JsfAllTests extends TestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for test.org.seasar.jsf");
		//$JUnit-BEGIN$
		suite.addTest(ElAllTests.suite());
		suite.addTest(ExceptionAllTests.suite());
		suite.addTest(JspAllTests.suite());
		suite.addTest(ProcessorAllTests.suite());
		suite.addTest(RuntimeAllTests.suite());
		suite.addTest(UtilAllTests.suite());
		//$JUnit-END$
		return suite;
	}
}
