package test.org.seasar.jsf.jsp;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.jsp.JspWriterImpl;

/**
 * @author higa
 *
 */
public class JspWriterImplTest extends S2TestCase {

	public JspWriterImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JspWriterImplTest.class);
	}
	
	public void testWrite() throws Exception {
		JspWriterImpl writer = new JspWriterImpl(getResponse(), 2, true);
		String s = "abcde";
		char[] cb = s.toCharArray();
		writer.write(cb);
		writer.flush();
		assertEquals("1", s, getResponse().getWriter().toString());
	}
}