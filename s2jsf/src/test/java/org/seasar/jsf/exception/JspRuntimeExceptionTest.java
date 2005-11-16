package org.seasar.jsf.exception;

import javax.servlet.jsp.JspException;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.JspRuntimeException;

/**
 * @author higa
 *
 */
public class JspRuntimeExceptionTest extends S2TestCase {

	public JspRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JspRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		RuntimeException ex = new JspRuntimeException(new JspException("hoge"));;
		System.out.println(ex.getMessage());
	}
}