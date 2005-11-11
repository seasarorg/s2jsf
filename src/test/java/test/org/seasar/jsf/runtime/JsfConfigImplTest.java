package test.org.seasar.jsf.runtime;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.runtime.ClassLoaderTaglibManagerImpl;
import org.seasar.jsf.runtime.JsfConfigImpl;

/**
 * @author higa
 *
 */
public class JsfConfigImplTest extends S2TestCase {

    private URLClassLoader loader;

    public JsfConfigImplTest(String arg0) throws MalformedURLException {
        super(arg0);
        File classRoot = ResourceUtil.getResourceAsFile(".");
        File projectRoot = classRoot.getParentFile().getParentFile();
        File lib = new File(projectRoot, "lib");
        File[] jars = lib.listFiles();
        URL[] jarUrls = new URL[jars.length];
        for (int i = 0; i < jars.length; i++) {
            jarUrls[i] = jars[i].toURL();
        }
        loader = new URLClassLoader(jarUrls);
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
        assertEquals("2", "h", config
            .getTaglibPrefix(JsfConstants.JSF_HTML_URI));
    }
}