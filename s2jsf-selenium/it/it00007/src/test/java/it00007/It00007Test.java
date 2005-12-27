package it00007;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

/*
 * ErrorPageManagerで遷移した先で再度例外が発生した場合に、
 * dispatchが無限ループしてしまう問題。
 */
public class It00007Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup testSetup = new WebApplicationTestSetup(It00007Test.class);
        return testSetup;
    }

    public void testHoge() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/first.html");
        try {
            selenium.verifyTextPresent("second error");
            fail();
        } catch (SeleniumException e) {
        }
        selenium.testComplete();
    }

}
