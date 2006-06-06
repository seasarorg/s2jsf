/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.jsf.processor;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author higa
 *
 */
public class ViewProcessorTest extends TeedaTestCase {

	public ViewProcessorTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ViewProcessorTest.class);
	}
	
	public void testSetContentType() throws Exception {
		ViewProcessor processor = new ViewProcessor();
		processor.setContentType("text/html; charset=Windows-31j");
		assertEquals("1", "Windows-31j", processor.getEncoding());
	}
    
    public void testSetExtendsPath() throws Exception {
        ViewProcessor processor = new ViewProcessor();
        MockApplication application = getApplication();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "hoge");
        application.setValueBinding(vb);
        processor.setExtendsPath("#{aaa}");
        assertEquals("hoge", processor.getExtendsPath());
    }
}