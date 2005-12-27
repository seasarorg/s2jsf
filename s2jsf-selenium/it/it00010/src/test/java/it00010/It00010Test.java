package it00010;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [Seasar-user:2969]
 * 
 */
public class It00010Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup testSetup = new WebApplicationTestSetup(
            It00010Test.class);
        return testSetup;
    }

    public void testHoge() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/a.html");
        {
            selenium.clickAndWait("form:b");
        }
        {
            verify(selenium);
            selenium.type("form:loop_0:val", "hoge");
            selenium.clickAndWait("form:a1");
            selenium.clickAndWait("form:b");
        }
        {
            verify(selenium);
            selenium.type("form:loop_1:val", "hoge");
            selenium.clickAndWait("form:a2");
            selenium.clickAndWait("form:b");
        }
        {
            verify(selenium);
            selenium.type("form:loop_2:val", "hoge");
            selenium.clickAndWait("form:a3");
            selenium.clickAndWait("form:b");
        }
        {
            verify(selenium);
            selenium.type("form:loop_3:val", "hoge");
            selenium.clickAndWait("form:a4");
            selenium.clickAndWait("form:b");
        }
        {
            verify(selenium);
            selenium.type("form:loop_3:val", "");
            selenium.clickAndWait("form:a1");
            selenium.clickAndWait("form:b");
        }
        {
            verify(selenium);
            selenium.type("form:loop_2:val", "");
            selenium.clickAndWait("form:a2");
            selenium.clickAndWait("form:b");
        }
        {
            verify(selenium);
            selenium.type("form:loop_1:val", "a");
            selenium.clickAndWait("form:a3");
            selenium.clickAndWait("form:b");
        }
        {
            verify(selenium);
            selenium.type("form:loop_1:val", "b");
            selenium.clickAndWait("form:a2");
            selenium.clickAndWait("form:b");
        }
        {
            verify(selenium);
            selenium.type("form:loop_1:val", "");
            selenium.clickAndWait("form:a4");
            // requiredエラー
            selenium.verifyTitle("Second Page");
            selenium.verifyValue("form:loop_0:val", "Item1000");
            selenium.verifyValue("form:loop_1:val", "");
            selenium.verifyValue("form:loop_2:val", "Item1002");
            selenium.verifyValue("form:loop_3:val", "Item1003");
        }

        selenium.testComplete();
    }

    private void verify(Selenium selenium) {
        selenium.verifyValue("form:loop_0:val", "Item1000");
        selenium.verifyValue("form:loop_1:val", "Item1001");
        selenium.verifyValue("form:loop_2:val", "Item1002");
        selenium.verifyValue("form:loop_3:val", "Item1003");
    }

}
