package it00006;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * 遷移前後の画面でformのidが等しい場合に、
 * 遷移先のinput値が破壊されてしまう問題。
 * 
 * form idが同じだと、遷移先の画面表示時にsubmitされたと誤認識されてしまい、
 * submittedValueに""がセットされてしまうため。
 */
public class It00006Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup testSetup = new WebApplicationTestSetup(It00006Test.class);
        return testSetup;
    }

    public void testHoge() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/first.html");
        {
            selenium.clickAndWait("aaa:doSubmit");
        }
        {
            selenium.verifyValue("aaa:bbb_0:inputHoge", "hoge1");
            selenium.verifyValue("aaa:bbb_1:inputHoge", "hoge2");
            selenium.verifyValue("aaa:bbb_2:inputHoge", "hoge3");
            selenium.clickAndWait("aaa:goPrevious");
        }
        {
            selenium.clickAndWait("aaa:doSubmit");
        }
        {
            selenium.verifyValue("aaa:bbb_0:inputHoge", "hoge1");
            selenium.verifyValue("aaa:bbb_1:inputHoge", "hoge2");
            selenium.verifyValue("aaa:bbb_2:inputHoge", "hoge3");
        }
        selenium.testComplete();
    }

}
