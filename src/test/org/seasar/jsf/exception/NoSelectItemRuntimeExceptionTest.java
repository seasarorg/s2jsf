package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.NoSelectItemRuntimeException;

/**
 * @author higa
 *  
 */
public class NoSelectItemRuntimeExceptionTest extends S2TestCase {

	public NoSelectItemRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(NoSelectItemRuntimeExceptionTest.class);
	}

	public void testGetMessage() throws Exception {
		String clientId = "hoge";
		NoSelectItemRuntimeException ex = new NoSelectItemRuntimeException(
				clientId);
		System.out.println(ex.getMessage());
		assertEquals("1", clientId, ex.getClientId());
	}
}