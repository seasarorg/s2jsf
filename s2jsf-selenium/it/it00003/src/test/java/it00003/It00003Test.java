package it00003;

import java.io.File;

import junit.framework.Test;

import org.apache.commons.io.FileUtils;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * org.apache.myfaces.ALLOW_JAVASCRIPTが"false"時に、aタグでの遷移が効かない
 */
public class It00003Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup testSetup = new WebApplicationTestSetup(It00003Test.class);
        return testSetup;
    }

    private final File webxml = new File(getApplicationFile(),
        "WEB-INF/web.xml");
    private File tmp_;

    protected void setUp() throws Exception {
        super.setUp();
        tmp_ = File.createTempFile("It00003Test", null);
        FileUtils.copyFile(webxml, tmp_);
    }

    protected void tearDown() throws Exception {
        FileUtils.copyFile(tmp_, webxml);
        super.tearDown();
    }

    public void testAllowJavascript_False() throws Exception {
        // ## Arrange ##
        FileUtils.copyFile(ResourceUtil
            .getResourceAsFile("web_allowJavascript_false.xml"), webxml);

        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/a.html");
        {
            selenium.clickAndWait("form:b");
        }
        {
            selenium.verifyLocation("/b.html");
            selenium.clickAndWait("form:a");
        }
        {
            selenium.verifyLocation("/a.html");
        }
        selenium.testComplete();
    }

    public void testAllowJavascript_True() throws Exception {
        // ## Arrange ##
        FileUtils.copyFile(ResourceUtil
            .getResourceAsFile("web_allowJavascript_true.xml"), new File(
            getApplicationFile(), "WEB-INF/web.xml"));

        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/a.html");
        {
            selenium.clickAndWait("form:b");
        }
        {
            selenium.verifyLocation("/b.html");
            selenium.clickAndWait("form:a");
        }
        {
            selenium.verifyLocation("/a.html");
        }
        selenium.testComplete();
    }

    public void testNoForm() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/c.html");
        selenium.verifyTextPresent("[EJSF0019]");

        selenium.testComplete();
    }

}
