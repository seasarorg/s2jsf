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