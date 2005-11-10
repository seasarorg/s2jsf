package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.PrefixNotFoundRuntimeException;

/**
 * @author higa
 *
 */
public class PrefixNotFoundRuntimeExceptionTest extends S2TestCase {

	public PrefixNotFoundRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PrefixNotFoundRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		RuntimeException ex = new PrefixNotFoundRuntimeException("z");
		System.out.println(ex.getMessage());
	}
}