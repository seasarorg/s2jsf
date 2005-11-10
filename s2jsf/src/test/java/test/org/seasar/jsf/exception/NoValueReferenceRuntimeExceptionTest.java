package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.NoValueReferenceRuntimeException;

/**
 * @author higa
 *
 */
public class NoValueReferenceRuntimeExceptionTest extends S2TestCase {

	public NoValueReferenceRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(NoValueReferenceRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		NoValueReferenceRuntimeException ex = new NoValueReferenceRuntimeException("hoge");
		System.out.println(ex.getMessage());
		assertEquals("1", "hoge", ex.getExpression());
	}
}