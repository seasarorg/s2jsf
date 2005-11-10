package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.StreamClosedException;

/**
 * @author higa
 *
 */
public class StreamClosedExceptionTest extends S2TestCase {

	public StreamClosedExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(StreamClosedExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		StreamClosedException ex = new StreamClosedException();
		System.out.println(ex.getMessage());
	}
}