package org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.UndefinedScopeRuntimeException;

/**
 * @author higa
 *
 */
public class UndefinedScopeRuntimeExceptionTest extends S2TestCase {

	public UndefinedScopeRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(UndefinedScopeRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		RuntimeException ex = new UndefinedScopeRuntimeException(99);
		System.out.println(ex.getMessage());
	}
}