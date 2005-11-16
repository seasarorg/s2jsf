package org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.component.UIText;
import org.seasar.jsf.exception.NoValueHolderRuntimeException;

/**
 * @author higa
 *
 */
public class NoValueHolderRuntimeExceptionTest extends S2TestCase {

	public NoValueHolderRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(NoValueHolderRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		NoValueHolderRuntimeException ex = new NoValueHolderRuntimeException(UIText.class);
		System.out.println(ex.getMessage());
	}
}