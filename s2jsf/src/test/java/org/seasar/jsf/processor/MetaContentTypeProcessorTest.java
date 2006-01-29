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

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.processor.MetaContentTypeProcessor;
import org.seasar.jsf.processor.ViewProcessor;

/**
 * @author higa
 *
 */
public class MetaContentTypeProcessorTest extends S2TestCase {

	public MetaContentTypeProcessorTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(MetaContentTypeProcessorTest.class);
	}
	
	public void testStartElement() throws Exception {
		ViewProcessor viewProcessor = new ViewProcessor();
		MetaContentTypeProcessor processor = new MetaContentTypeProcessor();
		processor.setProperty(JsfConstants.CONTENT_ATTR, "text/html; charset=Windows-31j");
		viewProcessor.addChild(processor);
		processor.setup(null, null, null, null, null);
		assertEquals("1", "Windows-31j", viewProcessor.getEncoding());
	}
}