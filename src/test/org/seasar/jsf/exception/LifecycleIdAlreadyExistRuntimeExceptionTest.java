package test.org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.LifecycleIdAlreadyExistRuntimeException;

/**
 * @author higa
 *
 */
public class LifecycleIdAlreadyExistRuntimeExceptionTest extends S2TestCase {

	public LifecycleIdAlreadyExistRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(LifecycleIdAlreadyExistRuntimeExceptionTest.class);
	}
	
	public void test() throws Exception {
		LifecycleIdAlreadyExistRuntimeException ex = new LifecycleIdAlreadyExistRuntimeException("hoge");;
		System.out.println(ex.getMessage());
		assertEquals("1", "hoge", ex.getLifecycleId());
	}
}