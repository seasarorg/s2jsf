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