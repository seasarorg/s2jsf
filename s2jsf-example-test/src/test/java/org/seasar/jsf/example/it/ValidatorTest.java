package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class ValidatorTest extends AbstractTestCase {

    public void test1() throws Exception {
        final String userNameErrorMessage = "id=User Name";
        final String ageErrorMessage = "適切な型へ変換できません";
        final String toNameErrorMessage = "\"To\": 値は\"From\"以上でなければいけません";

        HtmlPage page1 = getPageFromMenu("Validator");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("Validator", page1.getTitleText());
        StringAssert.assertContains("2 letters or more", body1);

        // 1

        HtmlTextInput userName1 = getUserName(page1);
        userName1.setValueAttribute("a");
        HtmlTextInput age1 = getAge(page1);
        age1.setValueAttribute("b");
        HtmlTextInput from1 = getFrom(page1);
        from1.setValueAttribute("b");
        HtmlTextInput to1 = getTo(page1);
        to1.setValueAttribute("a");

        StringAssert.assertNotContains(userNameErrorMessage, body1);
        StringAssert.assertNotContains(ageErrorMessage, body1);
        StringAssert.assertNotContains(toNameErrorMessage, body1);

        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();

        String body2 = getBody(page2).trim();
        System.out.println(body2);

        StringAssert.assertContains(userNameErrorMessage, body2);
        StringAssert.assertContains(ageErrorMessage, body2);
        StringAssert.assertContains(toNameErrorMessage, body2);

        HtmlTextInput userName2 = getUserName(page2);
        userName2.setValueAttribute("abc");
        HtmlTextInput age2 = getAge(page2);
        age2.setValueAttribute("32");
        HtmlTextInput from2 = getFrom(page2);
        from2.setValueAttribute("a");
        HtmlTextInput to2 = getTo(page2);
        to2.setValueAttribute("b");

        HtmlSubmitInput submit2 = getSubmit(page2);

        // 3

        HtmlPage page3 = (HtmlPage) submit2.click();

        String body3 = getBody(page3).trim();
        System.out.println(body3);

        StringAssert.assertNotContains(userNameErrorMessage, body3);
        StringAssert.assertNotContains(ageErrorMessage, body3);
        StringAssert.assertNotContains(toNameErrorMessage, body3);
    }

    private HtmlTextInput getUserName(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text'][1]")
                .selectSingleNode(form);
    }

    private HtmlTextInput getAge(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text'][2]")
                .selectSingleNode(form);
    }

    private HtmlTextInput getFrom(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text'][3]")
                .selectSingleNode(form);
    }

    private HtmlTextInput getTo(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text'][4]")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(".//input[@type='submit']")
                .selectSingleNode(form);
    }

}
