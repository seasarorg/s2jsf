package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.TagNotFoundRuntimeException;

/**
 * @author higa
 *
 */
public class TagNotFoundRuntimeExceptionTest extends S2TestCase {

	public TagNotFoundRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TagNotFoundRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		TagNotFoundRuntimeException ex = new TagNotFoundRuntimeException("hoge");
		System.out.println(ex.getMessage());
	}
}