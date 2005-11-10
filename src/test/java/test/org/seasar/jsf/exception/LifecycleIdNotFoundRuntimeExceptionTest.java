package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.LifecycleIdNotFoundRuntimeException;

/**
 * @author higa
 *
 */
public class LifecycleIdNotFoundRuntimeExceptionTest extends S2TestCase {

	public LifecycleIdNotFoundRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(LifecycleIdNotFoundRuntimeExceptionTest.class);
	}
	
	public void test() throws Exception {
		LifecycleIdNotFoundRuntimeException ex = new LifecycleIdNotFoundRuntimeException("hoge");;
		System.out.println(ex.getMessage());
		assertEquals("1", "hoge", ex.getLifecycleId());
	}
}