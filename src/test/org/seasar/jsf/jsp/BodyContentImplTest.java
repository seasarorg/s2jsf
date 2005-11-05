package test.org.seasar.jsf.jsp;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.jsp.BodyContentImpl;
import org.seasar.jsf.jsp.JspWriterImpl;

/**
 * @author higa
 *
 */
public class BodyContentImplTest extends S2TestCase {

	public BodyContentImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BodyContentImplTest.class);
	}
	
	public void testWrite() throws Exception {
		JspWriterImpl writer = new JspWriterImpl(getResponse(), 2, true);
		BodyContentImpl bodyContent = new BodyContentImpl(writer, 2);
		String s = "abcde";
		bodyContent.write(s);
		assertEquals("1", s, bodyContent.getString());
	}
}