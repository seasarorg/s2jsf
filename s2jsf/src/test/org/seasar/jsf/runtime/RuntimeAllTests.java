package test.org.seasar.jsf.runtime;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author higa
 *
 */
public class RuntimeAllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(RuntimeAllTests.class);
	}

	public static Test suite() {
		TestSuite suite =
			new TestSuite("Test for test.org.seasar.pages.impl");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(JsfConfigImplTest.class));
		suite.addTest(new TestSuite(TaglibConfigBuilderTest.class));
		suite.addTest(new TestSuite(TaglibManagerImplTest.class));
		suite.addTest(new TestSuite(TagPoolEntryTest.class));
		suite.addTest(new TestSuite(TagPoolImplTest.class));
		suite.addTest(new TestSuite(TagProcessorTreeFactoryImplTest.class));
		suite.addTest(new TestSuite(ViewTemplateFactoryImplTest.class));
		//$JUnit-END$
		return suite;
	}
}
