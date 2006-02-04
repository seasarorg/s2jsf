package it00016;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * 初期化Actionで例外発生→ErrorPageManagerで次のhtmlへ遷移した際に、
 * NullPointerExceptionが発生する問題。
 */
public class It00016Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
            It00016Test.class);
        return seleniumTestSetup;
    }

    public void testFoo() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/foo.html");
        selenium.verifyText("filename", "error.html");

        selenium.testComplete();
    }

}
