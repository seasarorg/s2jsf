package it00002;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [Seasar-user:2812]Listがネストしている際に、内側のListの要素数が異なると
 * submit時にjava.lang.ArrayIndexOutOfBoundsExceptionが発生する。
 */
public class It00002Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
            It00002Test.class);
        return seleniumTestSetup;
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
