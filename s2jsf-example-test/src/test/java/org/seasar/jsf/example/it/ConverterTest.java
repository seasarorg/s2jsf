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
public class ConverterTest extends AbstractTestCase {

    public void test1() throws Exception {
        HtmlPage page1 = getPageFromMenu("Converter");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("Converter", page1.getTitleText());
        StringAssert.assertContains("Hire Date:", body1);

        // 1

        HtmlTextInput input1 = getInput(page1);
        input1.setValueAttribute("21430923");
        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();

        String body2 = getBody(page2).trim();
        System.out.println(body2);

        HtmlTextInput input2 = getInput(page2);
        assertEquals("21430923", input2.getValueAttribute());
        StringAssert.assertContains("2143/09/23", body2);

        input2.setValueAttribute("aabb");
        HtmlSubmitInput submit2 = getSubmit(page2);
        final String validationFailureMessage = "aabb";
        StringAssert.assertNotContains(validationFailureMessage, body2);

        // 3

        HtmlPage page3 = (HtmlPage) submit2.click();
        String body3 = getBody(page3).trim();
        System.out.println(body3);
        StringAssert.assertContains(validationFailureMessage, body3);
    }

    private HtmlTextInput getInput(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text'][1]")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(".//input[@type='submit']")
                .selectSingleNode(form);
    }

}
