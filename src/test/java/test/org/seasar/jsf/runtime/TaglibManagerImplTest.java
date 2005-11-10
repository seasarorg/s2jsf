package test.org.seasar.jsf.runtime;

import java.net.URL;
import java.net.URLClassLoader;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.URLUtil;
import org.seasar.jsf.runtime.ClassLoaderTaglibManagerImpl;

/**
 * @author higa
 *
 */
public class TaglibManagerImplTest extends S2TestCase {
	
	private URLClassLoader loader;
	
	public TaglibManagerImplTest(String arg0) {
		super(arg0);
		URL url = ResourceUtil.getResource(".");
		URL url2 = URLUtil.create(url.toString() + "../lib/");
		loader = new URLClassLoader(new URL[]{url2});
	}
	
	protected ClassLoader getLoader() {
		return loader;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TaglibManagerImplTest.class);
	}
	
	public void testScanJars() throws Exception {
		ClassLoaderTaglibManagerImpl manager = new ClassLoaderTaglibManagerImpl();
		manager.scanJars(loader);
		assertTrue("1", manager.hasTaglibConfig("http://java.sun.com/jsf/html"));
	}
}