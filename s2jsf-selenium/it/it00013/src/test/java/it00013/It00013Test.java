package it00013;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

/*
 * [#7773][Seasar-user:3078]
 * 
 * spanタグのm:rendered=false時に、UIElement#afterContentsが出力されない問題。
 */
public class It00013Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
            It00013Test.class);
        return seleniumTestSetup;
    }

    public void testHoge() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/hoge.html");
        selenium.verifyTextPresent("A");
        // XXX verifyTextNotPresent
        try {
            selenium.verifyTextPresent("B");
            fail();
        } catch (SeleniumException expected) {
        }
        selenium.verifyTextPresent("C");
        selenium.verifyTextPresent("D");
        selenium.verifyTextPresent("E");

        selenium.testComplete();
    }

}
