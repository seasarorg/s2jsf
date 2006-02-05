package it00018;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * 動作確認用。
 */
public class It00018Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
                It00018Test.class);
        return seleniumTestSetup;
    }

    public void testInputBlankValueOrNullString() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/foo.html");

        selenium.type("form:a", "null");
        selenium.verifyText("form:textByValueBinding", "");
        selenium.clickAndWait("form:doSubmit");

        selenium.verifyValue("form:a", "null");
        selenium.verifyText("form:textByValueBinding", "null");
        selenium.type("form:a", "");
        selenium.clickAndWait("form:doSubmit");

        selenium.verifyText("form:textByValueBinding", "");
        selenium.verifyValue("form:a", "");
        selenium.type("form:a", "123");
        selenium.clickAndWait("form:doSubmit");

        selenium.verifyText("form:textByValueBinding", "123");
        selenium.verifyValue("form:a", "123");

        // //

        selenium.type("form:a", "null");
        selenium.clickAndWait("form:doCommandLink");

        selenium.verifyText("form:textByValueBinding", "null");
        selenium.verifyValue("form:a", "null");
        selenium.type("form:a", "");
        selenium.clickAndWait("form:doCommandLink");

        selenium.verifyText("form:textByValueBinding", "");
        selenium.verifyValue("form:a", "");
        selenium.type("form:a", "123");
        selenium.clickAndWait("form:doCommandLink");

        selenium.verifyText("form:textByValueBinding", "123");
        selenium.verifyValue("form:a", "123");

        // //

        selenium.type("form:a", "");
        selenium.clickAndWait("form:doSubmit");

        selenium.verifyValue("form:a", "");
        selenium.verifyText("form:textByValueBinding", "");
        selenium.verifyText("form:textByRequest", "");
        selenium.clickAndWait("form:doCommandLinkWithParam");

        selenium.verifyValue("form:a", "");
        selenium.verifyText("form:textByValueBinding", "");
        selenium.verifyText("form:textByRequest", "");
        selenium.verifyText("form:prevTextByRequest", "");
        selenium.type("form:a", "123");
        selenium.clickAndWait("form:doCommandLinkWithParam");

        selenium.verifyValue("form:a", "123");
        selenium.verifyText("form:textByValueBinding", "123");
        selenium.verifyText("form:textByRequest", "123");
        selenium.verifyText("form:prevTextByRequest", "");
        selenium.type("form:a", "456");
        selenium.clickAndWait("form:doCommandLinkWithParam");

        selenium.verifyValue("form:a", "456");
        selenium.verifyText("form:textByValueBinding", "456");
        selenium.verifyText("form:textByRequest", "456");
        selenium.verifyText("form:prevTextByRequest", "123");
        selenium.type("form:a", "");
        selenium.clickAndWait("form:doCommandLinkWithParam");

        selenium.verifyValue("form:a", "");
        selenium.verifyText("form:textByValueBinding", "");
        selenium.verifyText("form:textByRequest", "");
        selenium.verifyText("form:prevTextByRequest", "456");
        selenium.type("form:a", "null");
        selenium.clickAndWait("form:doCommandLinkWithParam");

        selenium.verifyValue("form:a", "null");
        selenium.verifyText("form:textByValueBinding", "null");
        selenium.verifyText("form:textByRequest", "null");
        selenium.verifyText("form:prevTextByRequest", "");
        selenium.type("form:a", "");
        selenium.clickAndWait("form:doCommandLinkWithParam");

        selenium.verifyValue("form:a", "");
        selenium.verifyText("form:textByValueBinding", "");
        selenium.verifyText("form:textByRequest", "");
        selenium.verifyText("form:prevTextByRequest", "null");

        selenium.testComplete();
    }

}
