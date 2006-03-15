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
public class AddTest extends AbstractTestCase {

    public void testAdd() throws Exception {
        HtmlPage page1 = getPageFromMenu("Add");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("Add", page1.getTitleText());
        StringAssert.assertContains("+", body1);

        // 1

        HtmlTextInput x1 = getX(page1);
        HtmlTextInput y1 = getY(page1);
        HtmlSubmitInput submit = getSubmit(page1);
        x1.setValueAttribute("12345678");
        y1.setValueAttribute("3");

        HtmlPage page2 = (HtmlPage) submit.click();

        // 2

        String body2 = getBody(page2).trim();
        System.out.println(body2);

        assertEquals("12345678", getX(page2).getValueAttribute());
        assertEquals("3", getY(page2).getValueAttribute());
        StringAssert.assertContains("12345681", body2);
    }

    private HtmlTextInput getX(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text'][1]")
                .selectSingleNode(form);
    }

    private HtmlTextInput getY(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text'][2]")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(".//input[@type='submit']")
                .selectSingleNode(form);
    }

}
