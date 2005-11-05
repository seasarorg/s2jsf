package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.NoHttpSessionRuntimeException;

/**
 * @author higa
 *
 */
public class NoHttpSessionRuntimeExceptionTest extends S2TestCase {

	public NoHttpSessionRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(NoHttpSessionRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		NoHttpSessionRuntimeException ex = new NoHttpSessionRuntimeException();
		System.out.println(ex.getMessage());
	}
}