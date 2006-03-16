package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
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

    public void no_test3() throws Exception {
        HtmlPage page1 = getPageFromMenu("Add");
        HtmlTextInput x1 = getX(page1);
        x1.setValueAttribute("123");
        HtmlSubmitInput submit = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit.click();
        HtmlTextInput x2 = getX(page2);
        assertEquals("123", x2.getValueAttribute());

        // 3

        HtmlAnchor addLink = page2.getFirstAnchorByText("Add");
        
        System.out.println(getBody(page2));
        
        System.out.println(addLink);
        Page click = addLink.click();
        System.out.println(click);
        HtmlPage page3 = (HtmlPage) click;
        HtmlTextInput x3 = getX(page3);
        assertEquals("123", x3.getValueAttribute());
    }

    public void no_test2() throws Exception {
        WebClient client = new WebClient();
        client.setThrowExceptionOnFailingStatusCode(false);
        HtmlPage menuPage = (HtmlPage) client.getPage(getUrl(""));
        HtmlAnchor helloLink1 = menuPage.getFirstAnchorByText("Add");
        HtmlPage helloPage1 = (HtmlPage) helloLink1.click();
        HtmlAnchor helloLink2 = helloPage1.getFirstAnchorByText("Add");
        helloLink2.click();
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
