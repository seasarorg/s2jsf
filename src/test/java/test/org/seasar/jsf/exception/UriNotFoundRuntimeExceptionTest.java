package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.UriNotFoundRuntimeException;

/**
 * @author higa
 *
 */
public class UriNotFoundRuntimeExceptionTest extends S2TestCase {

	public UriNotFoundRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(UriNotFoundRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		RuntimeException ex = new UriNotFoundRuntimeException("uri:hoge");
		System.out.println(ex.getMessage());
	}
}