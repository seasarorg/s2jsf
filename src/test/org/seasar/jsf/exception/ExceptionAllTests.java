package test.org.seasar.jsf.exception;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author higa
 *
 */
public class ExceptionAllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ExceptionAllTests.class);
	}

	public static Test suite() {
		TestSuite suite =
			new TestSuite("Test for test.org.seasar.jsf.exception");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(JspRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(LifecycleIdAlreadyExistRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(LifecycleIdNotFoundRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(NoEditableValueHolderRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(NoHttpSessionRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(NoSelectItemRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(NoUICommandRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(NoValueHolderRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(NoValueReferenceRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(PrefixNotFoundRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(StreamClosedExceptionTest.class));
		suite.addTest(new TestSuite(TagNotFoundRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(TagProcessorNotFoundRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(UndefinedScopeRuntimeExceptionTest.class));
		suite.addTest(new TestSuite(UriNotFoundRuntimeExceptionTest.class));
		//$JUnit-END$
		return suite;
	}
}
