package it00005;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * immediate=trueの場合に、submittedValueが""でセットされてしまう?
 */
public class It00005Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup testSetup = new WebApplicationTestSetup(
            It00005Test.class);
        return testSetup;
    }

    public void testJavascriptOn() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/test.html");
        {
            selenium.verifyText("testViewForm:spanUserId", "");
            selenium.verifyText("testViewForm:spanUserName", "");
            selenium.verifyValue("testViewForm:userID", "");
            selenium.verifyValue("testViewForm:userName", "");

            selenium.type("testViewForm:userID", "12");

            // john T carter
            selenium.clickAndWait("testViewForm:loop_0:selectNoImmediate");
        }
        {
            selenium.verifyTextPresent("値を入力して下さい");
            selenium.verifyText("testViewForm:spanUserId", "");
            selenium.verifyText("testViewForm:spanUserName", "");
            selenium.verifyValue("testViewForm:userID", "12");
            selenium.verifyValue("testViewForm:userName", "");

            selenium.clickAndWait("testViewForm:loop_0:selectImmediate");
        }
        {
            selenium.verifyText("testViewForm:spanUserId", "1000");
            selenium.verifyText("testViewForm:spanUserName", "john T carter");
            selenium.verifyValue("testViewForm:userID", "1000");
            selenium.verifyValue("testViewForm:userName", "john T carter");
        }
        selenium.testComplete();
    }

}
