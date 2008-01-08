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
package org.seasar.jsf.runtime;

import java.io.InputStream;

import org.apache.myfaces.taglib.html.HtmlColumnTag;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.TagConfig;
import org.seasar.jsf.TaglibConfig;
import org.seasar.jsf.runtime.TaglibConfigBuilder;

/**
 * @author higa
 *
 */
public class TaglibConfigBuilderTest extends S2TestCase {

	private static final String PATH = "org/seasar/jsf/runtime/myfaces-html.tld";
	
	public TaglibConfigBuilderTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TaglibConfigBuilderTest.class);
	}
	
	public void testBuild() throws Exception {
		TaglibConfigBuilder builder = new TaglibConfigBuilder();
		InputStream is = ResourceUtil.getResourceAsStream(PATH);
		TaglibConfig taglibConfig = builder.build(is);
		assertNotNull("1", taglibConfig);
		assertEquals("2", "http://java.sun.com/jsf/html", taglibConfig.getUri());
		TagConfig tagConfig = taglibConfig.getTagConfig("column");
		assertEquals("3", HtmlColumnTag.class, tagConfig.getTagClass());
	}
}