package it00019;

import java.io.File;

import junit.framework.Test;

import org.apache.commons.io.FileUtils;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [#7974][Seasar-user:3080]
 * 
 * jarファイルの外へtldファイルを配置できるようにする。
 * (これまではjar内のtldファイルを取得していた)
 */
public class It00019Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
                It00019Test.class);
        return seleniumTestSetup;
    }

    private final File webxml = new File(getApplicationFile(),
            "WEB-INF/web.xml");

    private File tmp_;

    protected void setUp() throws Exception {
        super.setUp();
        tmp_ = File.createTempFile("It00019Test", null);
        FileUtils.copyFile(webxml, tmp_);
    }

    protected void tearDown() throws Exception {
        FileUtils.copyFile(tmp_, webxml);
        super.tearDown();
    }

    public void testTaglib() throws Exception {
        doTest();
    }

    public void testNoTaglibDefinition() throws Exception {
        // ## Arrange ##
        FileUtils.copyFile(ResourceUtil.getResourceAsFile("web_notaglib.xml"),
                webxml);
        doTest();
    }

    private void doTest() {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/bar.html");

        selenium.verifyText("a", "abcabcabc");

        selenium.testComplete();
    }

}
