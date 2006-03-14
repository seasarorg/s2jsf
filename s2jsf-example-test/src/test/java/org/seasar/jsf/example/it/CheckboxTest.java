package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class CheckboxTest extends AbstractTestCase {

    public void test1() throws Exception {
        HtmlPage page1 = getPageFromMenu("Checkbox");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("Checkbox", page1.getTitleText());
        StringAssert.assertContains("hoge", body1);

        // 1
        HtmlCheckBoxInput checkbox1 = getCheckbox(page1);
        checkbox1.setChecked(true);

        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        HtmlCheckBoxInput checkbox2 = getCheckbox(page2);
        assertEquals(true, checkbox2.isChecked());
        StringAssert.assertContains("true", body2);

        checkbox2.setChecked(false);

        HtmlSubmitInput submit2 = getSubmit(page2);

        // 3

        HtmlPage page3 = (HtmlPage) submit2.click();
        String body3 = getBody(page3).trim();
        System.out.println(body3);

        HtmlCheckBoxInput checkbox3 = getCheckbox(page3);
        assertEquals(false, checkbox3.isChecked());
        StringAssert.assertContains("false", body3);
    }

    private HtmlCheckBoxInput getCheckbox(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlCheckBoxInput) new HtmlUnitXPath(
                "//input[@type='checkbox'][1]").selectSingleNode(form);
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath("//input[@type='submit']")
                .selectSingleNode(form);
    }

}
