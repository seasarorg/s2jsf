package it00012;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [#7749][Seasar-user:3045]
 * 
 * readonlyの場合はDTOへ入力値をセットするようにする。
 * 
 * これまではdisabledとreadonlyはDTOへ値をセットしていなかった。
 * 
 * disabledとreadonlyを同様に扱っていたが、disabledのみを特殊な扱いにする。
 * 
 * submit時に、disabledはrequest parameterで渡ってこないが
 * readonlyは渡ってくるため、この処置が可能となる。
 */
public class It00012Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup seleniumTestSetup = new WebApplicationTestSetup(
            It00012Test.class);
        return seleniumTestSetup;
    }

    public void testCheckbox() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/checkbox.html");
        {
            selenium.verifyValue("form:a", "on");
            selenium.verifyValue("form:b", "on");
            selenium.verifyValue("form:c", "on");
            selenium.clickAndWait("form:doSubmit");
        }
        {
            selenium.verifyValue("form:a", "on");
            selenium.verifyValue("form:b", "on");
            selenium.verifyValue("form:c", "on");

            // readonlyはpost時にパラメータが飛んで来ない
            // readonlyはパラメータが飛んでくる
            selenium.verifyText("form:requested_A", "false");
            selenium.verifyText("form:requested_B", "true");
            selenium.verifyText("form:requested_C", "true");

            // 
            // disabledとdisabledはdtoにパラメータが渡らない
            selenium.verifyText("form:dto_A", "false");
            selenium.verifyText("form:dto_B", "true");
            selenium.verifyText("form:dto_C", "true");
        }
        selenium.testComplete();
    }

    public void testInputSecret() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/inputSecret.html");
        {
            selenium.verifyValue("form:a", "");
            selenium.verifyValue("form:b", "");
            selenium.verifyValue("form:c", "");
            selenium.verifyValue("form:d", "d1");
            selenium.verifyValue("form:e", "e1");
            selenium.verifyValue("form:f", "f1");
            selenium.clickAndWait("form:doSubmit");
        }
        {
            selenium.verifyValue("form:a", "");
            selenium.verifyValue("form:b", "");
            selenium.verifyValue("form:c", "");
            selenium.verifyValue("form:d", "d1");
            selenium.verifyValue("form:e", "e1");
            selenium.verifyValue("form:f", "f1");

            selenium.verifyText("form:requested_A", "");
            selenium.verifyText("form:requested_B", "");
            selenium.verifyText("form:requested_C", "");
            selenium.verifyText("form:requested_D", "");
            selenium.verifyText("form:requested_E", "e1");
            selenium.verifyText("form:requested_F", "f1");

            selenium.verifyText("form:dto_A", "");
            selenium.verifyText("form:dto_B", "");
            selenium.verifyText("form:dto_C", "");
            selenium.verifyText("form:dto_D", "");
            selenium.verifyText("form:dto_E", "e1");
            selenium.verifyText("form:dto_F", "f1");
        }
        selenium.testComplete();
    }

    public void testInputText() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/inputText.html");
        {
            selenium.verifyValue("form:a", "a1");
            selenium.verifyValue("form:b", "b1");
            selenium.verifyValue("form:c", "c1");
            selenium.clickAndWait("form:doSubmit");
        }
        {
            selenium.verifyValue("form:a", "a1");
            selenium.verifyValue("form:b", "b1");
            selenium.verifyValue("form:c", "c1");

            selenium.verifyText("form:requested_A", "");
            selenium.verifyText("form:requested_B", "b1");
            selenium.verifyText("form:requested_C", "c1");

            selenium.verifyText("form:dto_A", "");
            selenium.verifyText("form:dto_B", "b1");
            selenium.verifyText("form:dto_C", "c1");
        }
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
        final String cInput = "//table[@id='form:c']//input";
        final String c1 = cInput + "[@value='1']";
        final String c2 = cInput + "[@value='2']";
        final String c3 = cInput + "[@value='3']";

        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/selectManyCheckbox.html");
        {
            selenium.verifyValue(a1, "on");
            selenium.verifyValue(a2, "on");
            selenium.verifyValue(a3, "on");
            selenium.verifyValue(b1, "on");
            selenium.verifyValue(b2, "on");
            selenium.verifyValue(b3, "on");
            selenium.verifyValue(c1, "on");
            selenium.verifyValue(c2, "on");
            selenium.verifyValue(c3, "on");

            selenium.clickAndWait("form:doSubmit");
        }
        {
            selenium.verifyValue(a1, "on");
            selenium.verifyValue(a2, "on");
            selenium.verifyValue(a3, "on");
            selenium.verifyValue(b1, "on");
            selenium.verifyValue(b2, "on");
            selenium.verifyValue(b3, "on");
            selenium.verifyValue(c1, "on");
            selenium.verifyValue(c2, "on");
            selenium.verifyValue(c3, "on");

            selenium.verifyText("form:requested_A", "");
            selenium.verifyText("form:requested_B", "1,2,3");
            selenium.verifyText("form:requested_C", "1,2,3");

            selenium.verifyText("form:dto_A", "");
            selenium.verifyText("form:dto_B", "1,2,3");
            selenium.verifyText("form:dto_C", "1,2,3");
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
            selenium.verifyValue("form:b", "2");
            selenium.verifyValue("form:c", "1");

            selenium.clickAndWait("form:doSubmit");
        }
        {
            selenium.verifyValue("form:a", "1");
            selenium.verifyValue("form:b", "2");
            selenium.verifyValue("form:c", "1");

            selenium.verifyText("form:requested_A", "");
            selenium.verifyText("form:requested_B", "2,3");
            selenium.verifyText("form:requested_C", "1,3");

            selenium.verifyText("form:dto_A", "");
            selenium.verifyText("form:dto_B", "2,3");
            selenium.verifyText("form:dto_C", "1,3");
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
            selenium.verifyValue("form:a", "1");
            selenium.verifyValue("form:b", "2");
            selenium.verifyValue("form:c", "3");

            selenium.clickAndWait("form:doSubmit");
        }
        {
            selenium.verifyValue("form:a", "1");
            selenium.verifyValue("form:b", "2");
            selenium.verifyValue("form:c", "3");

            selenium.verifyText("form:requested_A", "");
            selenium.verifyText("form:requested_B", "2");
            selenium.verifyText("form:requested_C", "3");

            selenium.verifyText("form:dto_A", "");
            selenium.verifyText("form:dto_B", "2");
            selenium.verifyText("form:dto_C", "3");
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
            selenium.verifyValue("form:a1", "on");
            selenium.verifyValue("form:a2", "off");
            selenium.verifyValue("form:a3", "off");
            selenium.verifyValue("form:b0", "off");
            selenium.verifyValue("form:b1", "off");
            selenium.verifyValue("form:b2", "on");
            selenium.verifyValue("form:b3", "off");
            selenium.verifyValue("form:c0", "off");
            selenium.verifyValue("form:c1", "off");
            selenium.verifyValue("form:c2", "off");
            selenium.verifyValue("form:c3", "on");

            selenium.clickAndWait("form:doSubmit");
        }
        {
            selenium.verifyValue("form:a0", "off");
            selenium.verifyValue("form:a1", "on");
            selenium.verifyValue("form:a2", "off");
            selenium.verifyValue("form:a3", "off");
            selenium.verifyValue("form:b0", "off");
            selenium.verifyValue("form:b1", "off");
            selenium.verifyValue("form:b2", "on");
            selenium.verifyValue("form:b3", "off");
            selenium.verifyValue("form:c0", "off");
            selenium.verifyValue("form:c1", "off");
            selenium.verifyValue("form:c2", "off");
            selenium.verifyValue("form:c3", "on");

            selenium.verifyText("form:requested_A", "");
            selenium.verifyText("form:requested_B", "2");
            selenium.verifyText("form:requested_C", "3");

            selenium.verifyText("form:dto_A", "");
            selenium.verifyText("form:dto_B", "2");
            selenium.verifyText("form:dto_C", "3");
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
            selenium.verifyValue("form:a", "a1");
            selenium.verifyValue("form:b", "b1");
            selenium.verifyValue("form:c", "c1");
            selenium.clickAndWait("form:doSubmit");
        }
        {
            selenium.verifyValue("form:a", "a1");
            selenium.verifyValue("form:b", "b1");
            selenium.verifyValue("form:c", "c1");

            selenium.verifyText("form:requested_A", "");
            selenium.verifyText("form:requested_B", "b1");
            selenium.verifyText("form:requested_C", "c1");

            selenium.verifyText("form:dto_A", "");
            selenium.verifyText("form:dto_B", "b1");
            selenium.verifyText("form:dto_C", "c1");
        }
        selenium.testComplete();
    }
}
