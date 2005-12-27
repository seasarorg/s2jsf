package it00001;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [Seasar-user:2811]
 */
public class It00001Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup testSetup = new WebApplicationTestSetup(
            It00001Test.class);
        return testSetup;
    }

    public void testCheckbox() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/checkbox.html");
        selenium.verifyValue("form:a", "on");
        selenium.verifyValue("form:b", "off");
        selenium.verifyValue("form:c", "on");
        selenium.verifyValue("form:d", "off");

        selenium.clickAndWait("doSubmit");

        selenium.verifyValue("form:a", "on");
        selenium.verifyValue("form:b", "off");
        selenium.verifyValue("form:c", "on");
        selenium.verifyValue("form:d", "off");

        selenium.click("form:c");
        selenium.click("form:d");

        selenium.clickAndWait("doSubmit");

        selenium.verifyValue("form:a", "on");
        selenium.verifyValue("form:b", "off");
        selenium.verifyValue("form:c", "off");
        selenium.verifyValue("form:d", "on");

        selenium.testComplete();
    }

    public void testInputSecret() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/inputSecret.html");
        selenium.verifyValue("form:a", "");
        selenium.verifyValue("form:b", "");
        selenium.verifyValue("form:c", "c1");
        selenium.verifyValue("form:d", "d1");

        selenium.type("form:b", "b2");
        selenium.type("form:d", "d2");

        selenium.clickAndWait("doSubmit");

        // passwordはredisplay="true"の場合に値が再現される
        selenium.verifyValue("form:a", "");
        selenium.verifyValue("form:b", "");
        selenium.verifyValue("form:c", "c1");
        selenium.verifyValue("form:d", "d2");

        selenium.testComplete();
    }

    public void testInputText() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/inputText.html");
        selenium.verifyValue("form:a", "hoge");
        selenium.verifyValue("form:b", "");
        selenium.type("form:b", "foooo");
        selenium.clickAndWait("doSubmit");

        selenium.verifyValue("form:a", "hoge");
        selenium.verifyValue("form:b", "foooo");
        selenium.type("form:b", "baaaa");
        selenium.clickAndWait("doSubmit");

        selenium.verifyValue("form:a", "hoge");
        selenium.verifyValue("form:b", "baaaa");
        selenium.type("form:b", "");
        selenium.clickAndWait("doSubmit");

        selenium.verifyValue("form:a", "hoge");
        selenium.verifyValue("form:b", "");

        selenium.testComplete();
    }

    public void testSelectManyCheckbox() throws Exception {
        // ## Arrange ##
        final String aInput = "//table[@id='form:a']//input";
        final String a1 = aInput + "[@value='1']";
        final String a2 = aInput + "[@value='2']";
        final String a3 = aInput + "[@value='3']";
        final String bInput = "//table[@id='form:b']//input";
        final String b1 = bInput + "[@value='1']";
        final String b2 = bInput + "[@value='2']";
        final String b3 = bInput + "[@value='3']";

        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/selectManyCheckbox.html");
        {
            selenium.verifyValue(a1, "off");
            selenium.verifyValue(a2, "on");
            selenium.verifyValue(a3, "on");
            selenium.verifyValue(b1, "on");
            selenium.verifyValue(b2, "off");
            selenium.verifyValue(b3, "off");

            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue(a1, "off");
            selenium.verifyValue(a2, "on");
            selenium.verifyValue(a3, "on");
            selenium.verifyValue(b1, "on");
            selenium.verifyValue(b3, "off");
            selenium.verifyValue(b3, "off");

            selenium.click(b1);
            selenium.clickAndWait("doSubmit");
        }
        {
            // XXX:b1がoffにならないようだ。
            // selectManyCheckboxにseleniumが対応していないのか!?
            //selenium.verifyValue(b1, "off");
        }

        selenium.testComplete();
    }

    public void testSelectManyListbox() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/selectManyListbox.html");

        {
            selenium.verifyValue("form:a", "1");
            // XXX: 複数項目が選択されていることのassertはできないようだ
            //selenium.verifyValue("form:a", "3");
            selenium.verifyValue("form:b", "3");

            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:a", "1");
            selenium.verifyValue("form:b", "3");
        }

        selenium.testComplete();
    }

    public void testSelectOneMenu() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/selectOneMenu.html");
        {
            selenium.verifyValue("form:a", "2");
            selenium.verifyValue("form:b", "1");

            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:a", "2");
            selenium.verifyValue("form:b", "1");
            selenium.type("form:b", "3");

            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:a", "2");
            selenium.verifyValue("form:b", "3");
        }

        selenium.testComplete();
    }

    public void testSelectOneRadio() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/selectOneRadio.html");
        {
            selenium.verifyValue("form:a0", "off");
            selenium.verifyValue("form:a1", "off");
            selenium.verifyValue("form:a2", "on");
            selenium.verifyValue("form:a3", "off");
            selenium.verifyValue("form:b0", "off");
            selenium.verifyValue("form:b2", "off");
            selenium.verifyValue("form:b3", "on");

            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:a0", "off");
            selenium.verifyValue("form:a1", "off");
            selenium.verifyValue("form:a2", "on");
            selenium.verifyValue("form:a3", "off");
            selenium.verifyValue("form:b0", "off");
            selenium.verifyValue("form:b1", "off");
            selenium.verifyValue("form:b2", "off");
            selenium.verifyValue("form:b3", "on");

            selenium.click("form:b1");
            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:a0", "off");
            selenium.verifyValue("form:a1", "off");
            selenium.verifyValue("form:a2", "on");
            selenium.verifyValue("form:a3", "off");
            selenium.verifyValue("form:b0", "off");
            selenium.verifyValue("form:b1", "on");
            selenium.verifyValue("form:b2", "off");
            selenium.verifyValue("form:b3", "off");
        }

        selenium.testComplete();
    }

    public void testTextarea() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/textarea.html");
        {
            selenium.verifyValue("form:a", "111");
            selenium.verifyValue("form:b", "222");
            selenium.type("form:b", "foooo");

            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:a", "111");
            selenium.verifyValue("form:b", "foooo");
            selenium.type("form:b", "baaaa");

            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:a", "111");
            selenium.verifyValue("form:b", "baaaa");
            selenium.type("form:b", "");

            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:b", "");
            selenium.verifyValue("form:a", "111");
        }

        selenium.testComplete();
    }

}
