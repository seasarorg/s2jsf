package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class InputTextareaTest extends AbstractTestCase {

    public void testInputTextarea() throws Exception {
        HtmlPage page1 = getPageFromMenu("InputTextarea");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("InputTextarea", page1.getTitleText());
        StringAssert.assertContains("Textarea1:", body1);

        // 1

        HtmlTextArea a1 = getA(page1);
        a1.setText("aaaaaa\n\nbb");

        HtmlTextArea age1 = getAge(page1);
        age1.setText("42");

        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        HtmlTextArea a2 = getA(page1);
        assertEquals("aaaaaa\n\nbb", a2.getText());

        HtmlTextArea age2 = getAge(page1);
        assertEquals("42", age2.getText());
    }

    private HtmlTextArea getA(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlTextArea) new HtmlUnitXPath(".//textarea[1]")
                .selectSingleNode(form);
    }

    private HtmlTextArea getAge(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlTextArea) new HtmlUnitXPath(".//textarea[2]")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(".//input[@type='submit']")
                .selectSingleNode(form);
    }

}
