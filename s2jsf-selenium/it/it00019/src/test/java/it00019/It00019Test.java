package it00019;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [#7974][Seasar-user:3080]
 * 
 * jarファイルの外へtldファイルを配置できるようにする。
 * (これまではjar内のtldファイルを取得していた)
 */
public class It00019Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
                It00019Test.class);
        return seleniumTestSetup;
    }

    public void testTaglib() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/foo.html");

        selenium.verifyText("a", "abcabcabc");

        selenium.testComplete();
    }

}
