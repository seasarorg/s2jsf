package test.org.seasar.jsf.processor;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.processor.ViewProcessor;

/**
 * @author higa
 *
 */
public class ViewProcessorTest extends S2TestCase {

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
}