package it00017;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * 初期化Actionで別画面へredirectで遷移する際に、
 * その初期化Actionが2度実行されてしまう問題。
 */
public class It00017Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
            It00017Test.class);
        return seleniumTestSetup;
    }

    public void testFoo() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/first.html");
        selenium.verifyLocation("/second.html");
        selenium.verifyText("form:b", "1");

        selenium.testComplete();
    }

}
