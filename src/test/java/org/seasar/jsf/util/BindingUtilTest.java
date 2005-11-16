package org.seasar.jsf.util;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.util.BindingUtil;

/**
 * @author higa
 *  
 */
public class BindingUtilTest extends S2TestCase {
	
	public BindingUtilTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BindingUtilTest.class);
	}

	public void testIsValueReference() throws Exception {
		assertEquals("1", true, BindingUtil.isValueReference("#{hoge.aaa}"));
		assertEquals("2", false, BindingUtil.isValueReference("${hoge.aaa}"));
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}