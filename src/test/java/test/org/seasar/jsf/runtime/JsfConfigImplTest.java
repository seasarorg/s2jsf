package test.org.seasar.jsf.runtime;

import java.net.URL;
import java.net.URLClassLoader;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.URLUtil;
import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.runtime.JsfConfigImpl;
import org.seasar.jsf.runtime.ClassLoaderTaglibManagerImpl;

/**
 * @author higa
 *
 */
public class JsfConfigImplTest extends S2TestCase {
	
	private URLClassLoader loader;
	
	public JsfConfigImplTest(String arg0) {
		super(arg0);
		URL url = ResourceUtil.getResource(".");
		URL url2 = URLUtil.create(url.toString() + "../lib/");
		loader = new URLClassLoader(new URL[]{url2});
	}
	
	protected ClassLoader getLoader() {
		return loader;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JsfConfigImplTest.class);
	}
	
	public void test() throws Exception {
		ClassLoaderTaglibManagerImpl manager = new ClassLoaderTaglibManagerImpl();
		manager.scanJars(loader);
		JsfConfig config = new JsfConfigImpl();
		config.setTaglibManager(manager);
		config.addTaglibUri("h", JsfConstants.JSF_HTML_URI);
		assertNotNull("1", config.getTagConfig("h:inputText"));
		assertEquals("2", "h", config.getTaglibPrefix(JsfConstants.JSF_HTML_URI));
	}
}