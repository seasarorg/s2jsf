package it00008;

import java.io.File;

import junit.framework.Test;

import org.apache.commons.io.FileUtils;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [#7682]
 * 
 * redirectの場合にaタグ配下のf:paramが効かない問題。
 * -> [XXX] pending
 */
public class It00008Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup testSetup = new WebApplicationTestSetup(
            It00008Test.class);
        return testSetup;
    }

    private final File webxml = new File(getApplicationFile(),
        "WEB-INF/web.xml");

    private File tmp_;

    protected void setUp() throws Exception {
        super.setUp();
        tmp_ = File.createTempFile("It00008Test", null);
        FileUtils.copyFile(webxml, tmp_);
    }

    protected void tearDown() throws Exception {
        FileUtils.copyFile(tmp_, webxml);
        super.tearDown();
    }

    // TODO
    public void pending_testJavascriptOn() throws Exception {
        // ## Arrange ##
        FileUtils.copyFile(ResourceUtil
            .getResourceAsFile("web_allowJavascript_true.xml"), webxml);
        doTest();
    }

    private void doTest() {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/first.html");
        {
            selenium.clickAndWait("form:goSecond");
        }
        {
            selenium.verifyText("actual", "Hello World");
        }
        selenium.testComplete();
    }

    public void testJavascriptOff() throws Exception {
        // ## Arrange ##
        FileUtils.copyFile(ResourceUtil
            .getResourceAsFile("web_allowJavascript_false.xml"), webxml);
        doTest();
    }

}
