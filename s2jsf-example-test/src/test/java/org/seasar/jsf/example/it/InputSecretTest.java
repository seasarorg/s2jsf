package org.seasar.jsf.example.it;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class InputSecretTest extends AbstractTestCase {

    public void test1() throws Exception {
        HtmlPage page1 = getPageFromMenu("InputSecret");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("InputSecret", page1.getTitleText());

        // 1

        HtmlPasswordInput a1 = getPassword(page1);
        a1.setValueAttribute("abcde");

        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        HtmlPasswordInput a2 = getPassword(page2);
        assertEquals("", a2.getValueAttribute());
    }

    private HtmlPasswordInput getPassword(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlPasswordInput) new HtmlUnitXPath(
                ".//input[@type='password']").selectSingleNode(form);
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(".//input[@type='submit']")
                .selectSingleNode(form);
    }

}
