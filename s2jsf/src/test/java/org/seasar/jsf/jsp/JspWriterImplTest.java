/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.jsf.jsp;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.jsf.jsp.JspWriterImpl;

/**
 * @author higa
 * @author yone
 */
public class JspWriterImplTest extends S2TestCase {

	public JspWriterImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JspWriterImplTest.class);
	}
	
	public void testWrite() throws Exception {
        final MockHttpServletResponse res = getResponse();
		JspWriterImpl writer = new JspWriterImpl(res, 2, true);
		String s = "abcde";
		char[] cb = s.toCharArray();
		writer.write(cb);
		writer.flush();
		assertEquals("1", s, new String(res.getResponseBytes()));
	}
}