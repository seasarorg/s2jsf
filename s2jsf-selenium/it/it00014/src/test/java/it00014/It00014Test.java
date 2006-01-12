package it00014;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [#7506][Seasar-user:2792]
 * 
 * ページを継承しないと初期化アクションでエラーが発生する問題。
 */
public class It00014Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
            It00014Test.class);
        return seleniumTestSetup;
    }

    public void testHoge() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/a.html");
        selenium.verifyTextPresent("これは\"a.html\"です。");

        selenium.open("/b_child.html");
        selenium.verifyTextPresent("これは\"b_parent.html\"です。");

        selenium.testComplete();
    }

}
