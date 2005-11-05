package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.component.UIText;
import org.seasar.jsf.exception.NoUICommandRuntimeException;

/**
 * @author higa
 *
 */
public class NoUICommandRuntimeExceptionTest extends S2TestCase {

	public NoUICommandRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(NoUICommandRuntimeExceptionTest.class);
	}
	
	public void testGetMessageAndProperty() throws Exception {
		NoUICommandRuntimeException ex = new NoUICommandRuntimeException(UIText.class);
		System.out.println(ex.getMessage());
		assertNotNull("1", ex.getComponentClass());
	}
}