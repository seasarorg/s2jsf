package test.org.seasar.jsf.runtime;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.runtime.TagPoolEntry;
import org.seasar.jsf.taglib.ElementTag;

/**
 * @author higa
 *
 */
public class TagPoolEntryTest extends S2TestCase {

	public TagPoolEntryTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TagPoolEntryTest.class);
	}
	
	public void testRequestAndRelease() throws Exception {
		TagPoolEntry entry = new TagPoolEntry(ElementTag.class);
		ElementTag tag = (ElementTag) entry.request();
		assertNotNull("1", tag);
		entry.release(tag);
		assertEquals("2", tag, entry.request());
	}
}