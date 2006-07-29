package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class PostbackTest extends AbstractTestCase {

    public void testPostback() throws Exception {
        final String postbackTrue = "postback = true";
        final String postbackFalse = "postback = false";

        final HtmlPage page1 = getPageFromMenu("Postback");
        final String body1 = getBody(page1).trim();
        System.out.println(body1);

        // 1

        assertEquals("Postback", page1.getTitleText());
        StringAssert.assertContains(postbackFalse, body1);
        StringAssert.assertNotContains(postbackTrue, body1);
        final HtmlSubmitInput submit = getSubmit(page1);

        // 2

        final HtmlPage page2 = (HtmlPage) submit.click();
        final String body2 = getBody(page2).trim();
        System.out.println(body2);
        StringAssert.assertNotContains(postbackFalse, body2);
        StringAssert.assertContains(postbackTrue, body2);
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(".//input[@type='submit']")
                .selectSingleNode(form);
    }

}
