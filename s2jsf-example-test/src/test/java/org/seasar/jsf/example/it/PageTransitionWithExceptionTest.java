package org.seasar.jsf.example.it;

import java.io.IOException;
import java.net.MalformedURLException;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class PageTransitionWithExceptionTest extends AbstractTestCase {

    public void testPageTransitionWithException1() throws Exception {
        HtmlPage page1 = getPage();

        // 1

        HtmlSubmitInput a1 = getSubmitA(page1);

        // 2

        HtmlPage page2 = (HtmlPage) a1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);
        StringAssert.assertContains("errorPage1", body2);
    }

    public void testPageTransitionWithException2() throws Exception {
        HtmlPage page1 = getPage();

        // 1

        HtmlSubmitInput a1 = getSubmitB(page1);

        // 2

        HtmlPage page2 = (HtmlPage) a1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);
        StringAssert.assertContains("errorPage2", body2);
    }

    private HtmlPage getPage() throws MalformedURLException, IOException {
        HtmlPage page = getPageFromMenu("The page transition with exception");
        String body1 = getBody(page).trim();
        System.out.println(body1);
        assertEquals("The page transition with exception", page.getTitleText());
        return page;
    }

    private HtmlSubmitInput getSubmitA(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(
                ".//input[@type='submit'][1]").selectSingleNode(form);
    }

    private HtmlSubmitInput getSubmitB(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(
                ".//input[@type='submit'][2]").selectSingleNode(form);
    }

}
