package test.org.seasar.jsf.processor;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author higa
 *
 */
public class ProcessorAllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ProcessorAllTests.class);
	}

	public static Test suite() {
		TestSuite suite =
			new TestSuite("Test for test.org.seasar.jsf.processor");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(MetaContentTypeProcessorTest.class));
		suite.addTest(new TestSuite(ViewProcessorTest.class));
		//$JUnit-END$
		return suite;
	}
}
