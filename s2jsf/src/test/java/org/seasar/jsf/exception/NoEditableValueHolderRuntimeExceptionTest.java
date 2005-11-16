package org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.component.UIText;
import org.seasar.jsf.exception.NoEditableValueHolderRuntimeException;

/**
 * @author higa
 *
 */
public class NoEditableValueHolderRuntimeExceptionTest extends S2TestCase {

	public NoEditableValueHolderRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(NoEditableValueHolderRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		NoEditableValueHolderRuntimeException ex = new NoEditableValueHolderRuntimeException(UIText.class);
		System.out.println(ex.getMessage());
	}
}