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

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author cero-t
 * 
 */
public class InsertProcessorTest extends TeedaTestCase {

	public InsertProcessorTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(InsertProcessorTest.class);
	}

	public void testGetSrcs() throws Exception {
		InsertProcessor ip = new InsertProcessor("insert");

		MockApplication application = getApplication();
		MockValueBinding vb = new MockValueBinding();
		application.setValueBinding(vb);

		ip.setProperty("src", "/page.html");
		String[] srcs = ip.getSrcs();
		assertEquals("/page.html", srcs[0]);

		vb.setValue(getFacesContext(), "/page0.html");
		ip.setProperty("src", "#{xxx}");
		srcs = ip.getSrcs();
		assertEquals("/page0.html", srcs[0]);

		vb.setValue(getFacesContext(), new String[] { "/page1.html", "/page2.html" });
		ip.setProperty("src", "#{xxx}");
		srcs = ip.getSrcs();
		assertEquals("/page1.html", srcs[0]);
		assertEquals("/page2.html", srcs[1]);

		List list = new ArrayList();
		list.add("/page3.html");
		list.add("/page4.html");
		vb.setValue(getFacesContext(), list);
		ip.setProperty("src", "#{xxx}");
		srcs = ip.getSrcs();
		assertEquals("/page3.html", srcs[0]);
		assertEquals("/page4.html", srcs[1]);
	}
}
