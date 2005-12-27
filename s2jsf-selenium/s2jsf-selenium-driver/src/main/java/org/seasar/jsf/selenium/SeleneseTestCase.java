package org.seasar.jsf.selenium;

import java.io.File;

import junit.framework.TestCase;

import org.mortbay.util.StringUtil;
import org.seasar.framework.util.ResourceUtil;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.launchers.SystemDefaultBrowserLauncher;

public abstract class SeleneseTestCase extends TestCase {

    private LazySelenium _selenium;
    private String _originalJettyEncoding;
    private MyJettyCommandProcessor _commandProcessor;

    protected void setUp() throws Exception {
        super.setUp();
        _originalJettyEncoding = StringUtil.__ISO_8859_1;
        StringUtil.__ISO_8859_1 = getHtmlEncoding();
        _commandProcessor = new MyJettyCommandProcessor();
        _commandProcessor.deploySeleniumDriver(getSeleniumWarFile());
        _commandProcessor.addWebApplication(getApplicationFile());
    }

    protected File getApplicationFile() {
        File file = ResourceUtil.getFile(getClass().getResource("."));
        for (File f = file; f != null; f = f.getParentFile()) {
            File pomFile = new File(f, "pom.xml");
            if (pomFile.exists()) {
                File project = pomFile.getParentFile();
                return new File(project, "target/" + project.getName());
            }
        }
        throw new IllegalStateException("pom.xml does not found");
    }

    protected File getSeleniumWarFile() {
        return new File(TestUtil.getProjectRootPath(),
            "selenium-driver-0.6.war");
    }

    protected String getHtmlEncoding() {
        return "UTF-8";
    }

    protected void tearDown() throws Exception {
        if (_selenium != null && _selenium.isStart()) {
            try {
                _selenium.stop();
            } catch (Error th) {
                th.printStackTrace();
                throw th;
            }
        }
        StringUtil.__ISO_8859_1 = _originalJettyEncoding;
        super.tearDown();
    }

    protected Selenium getSelenium() {
        if (_selenium == null) {
            try {
                LazySelenium lazySelenum = new LazySelenium();
                lazySelenum.setSelenium(new DefaultSelenium(_commandProcessor,
                    new SystemDefaultBrowserLauncher()));
                _selenium = lazySelenum;
            } catch (Error th) {
                th.printStackTrace();
                throw th;
            }
        }
        return _selenium;
    }

    protected MyJettyCommandProcessor getCommandProcessor() {
        return _commandProcessor;
    }

}
