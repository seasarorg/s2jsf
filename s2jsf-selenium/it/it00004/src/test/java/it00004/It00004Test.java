package it00004;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * javax.faces.STATE_SAVING_METHOD=client時に状態が保存されない問題
 * 
 * it00002のweb.xmlだけを変更している。
 */
public class It00004Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup testSetup = new WebApplicationTestSetup(It00004Test.class);
        return testSetup;
    }

    public void testHoge() throws Exception {
        // ## Arrange ##
        final String a2 = "//form[@id='form']//input[2]";
        final String a3 = "//form[@id='form']//input[3]";
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/hoge.html");
        {
            selenium.verifyValue(a2, "a2");
            selenium.verifyValue(a3, "a3");
            selenium.type(a2, "a2-2");
            selenium.type(a3, "a3-2");
            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue(a2, "a2-2");
            selenium.verifyValue(a3, "a3-2");
        }
        selenium.testComplete();
    }

}
