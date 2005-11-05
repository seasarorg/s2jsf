package test.org.seasar.jsf.runtime;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.TagPool;
import org.seasar.jsf.runtime.TagPoolImpl;
import org.seasar.jsf.taglib.ElementTag;

/**
 * @author higa
 *
 */
public class TagPoolImplTest extends S2TestCase {

	public TagPoolImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TagPoolImplTest.class);
	}
	
	public void testRequestAndRelease() throws Exception {
		TagPool pool = new TagPoolImpl();
		ElementTag tag = (ElementTag) pool.request(ElementTag.class);
		assertNotNull("1", tag);
		pool.release(tag);
		assertEquals("2", tag, pool.request(ElementTag.class));
	}
}