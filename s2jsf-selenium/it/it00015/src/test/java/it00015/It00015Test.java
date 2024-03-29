package it00015;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [7840][Seasar-user:3083]f:paramで最新の値が評価されない
 * 
 * 値の表示が1画面遅れてしまう。
 */
public class It00015Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
            It00015Test.class);
        return seleniumTestSetup;
    }

    public void testReflectNewestValueToFParam() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/foo.html");
        selenium.verifyValue("form:a", "A");
        selenium.verifyText("form:bean", "A");
        selenium.verifyText("form:param", "A");

        selenium.type("form:a", "い");
        selenium.clickAndWait("doSubmit");

        selenium.verifyValue("form:a", "い");
        selenium.verifyText("form:bean", "い");
        selenium.verifyText("form:param", "い");

        selenium.testComplete();
    }

    // initialize actionで変更した値がf:paramへ適用されること
    public void testReflectNewestValueToFParamByInitializeAction()
        throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/bar.html");
        selenium.verifyValue("form:a", "B");
        selenium.verifyText("form:bean", "B");
        selenium.verifyText("form:param", "B");

        selenium.testComplete();
    }

}
