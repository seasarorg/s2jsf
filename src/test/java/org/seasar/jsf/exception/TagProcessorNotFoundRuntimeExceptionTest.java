package org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.TagProcessor;
import org.seasar.jsf.exception.TagProcessorNotFoundRuntimeException;

/**
 * @author higa
 *
 */
public class TagProcessorNotFoundRuntimeExceptionTest extends S2TestCase {

	public TagProcessorNotFoundRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TagProcessorNotFoundRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		RuntimeException ex = new TagProcessorNotFoundRuntimeException(TagProcessor.class);
		System.out.println(ex.getMessage());
	}
}