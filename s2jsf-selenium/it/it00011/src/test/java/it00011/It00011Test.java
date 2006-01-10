package it00011;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

/*
 * [#7645][Seasar-user:2788]
 * 
 * リソースファイルに書いたbrタグが、h:messageで表示
 * する際にURLエンコードされてしまう。
 * 
 * h:message, h:messagesでリソースファイルから出力する文字列では、タグを
 * エスケープしないようにする。
 */
public class It00011Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
            It00011Test.class);
        return seleniumTestSetup;
    }

    public void testMessages() throws Exception {
        // ## Arrange ##
        final String a1 = "//form[@id='form']/ul/li[1]/span";
        final String a2 = "//form[@id='form']/ul/li[2]/span";
        final String a3 = "//form[@id='form']/ul/li[3]/span";
        final String a4 = "//form[@id='form']/ul/li[4]/span";
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/messages.html");
        selenium.clickAndWait("form:showMessages");
        selenium.verifyElementPresent(a1);
        selenium.verifyAttribute(a1 + "/@class", "fooFatalClass");
        // TODO style属性を取得できないようだ
        //selenium.verifyAttribute(a1 + "/@style", "fooFatalStyle");

        selenium.verifyElementPresent(a2);
        selenium.verifyAttribute(a2 + "/@class", "fooErrorClass");
        //selenium.verifyAttribute(a2 + "/@style", "fooFatalStyle");

        selenium.verifyElementPresent(a3);
        selenium.verifyAttribute(a3 + "/@class", "fooWarnClass");
        //selenium.verifyAttribute(a3 + "/@style", "fooFatalStyle");

        selenium.verifyElementPresent(a4);
        selenium.verifyAttribute(a4 + "/@class", "fooInfoClass");
        //selenium.verifyAttribute(a4 + "/@style", "fooFatalStyle");

        try {
            selenium.verifyTextPresent("<br/>");
            fail("リソース中のタグはエスケープされること");
        } catch (SeleniumException expected) {
        }

        selenium.testComplete();
    }

    public void testMessage() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/message.html");
        selenium.clickAndWait("form:showMessage");
        //TODO style属性を取得できないようだ
        //selenium.verifyAttribute("form:a_message@style", "fooFatalStyle");
        selenium.verifyAttribute("form:a_message@class", "fooFatalClass");
        //selenium.verifyAttribute("form:b_message@style", "fooErrorStyle");
        selenium.verifyAttribute("form:b_message@class", "fooErrorClass");
        //selenium.verifyAttribute("form:c_message@style", "fooWarnStyle");
        selenium.verifyAttribute("form:c_message@class", "fooWarnClass");
        //selenium.verifyAttribute("form:d_message@style", "fooInfoStyle");
        selenium.verifyAttribute("form:d_message@class", "fooInfoClass");

        try {
            selenium.verifyTextPresent("<br/>");
            fail("リソース中のタグはエスケープされること");
        } catch (SeleniumException expected) {
        }
        selenium.testComplete();
    }

}
