package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class SelectManyListboxTest extends AbstractTestCase {

    public void test1() throws Exception {
        HtmlPage page1 = getPageFromMenu("SelectManyListbox");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("SelectManyListbox", page1.getTitleText());
        StringAssert.assertContains("Aaa:", body1);

        // 1

        {
            HtmlSelect select = getA(page1);
            assertEquals(true, select.isMultipleSelectEnabled());
            assertEquals(3, select.getOptionSize());
            assertEquals(false, select.getOption(0).isSelected());
            assertEquals(true, select.getOption(1).isSelected());
            assertEquals(false, select.getOption(2).isSelected());
            select.getOption(2).setSelected(true);
        }
        {
            HtmlSelect select = getB(page1);
            assertEquals(true, select.isMultipleSelectEnabled());
            assertEquals(4, select.getOptionSize());
            assertEquals(false, select.getOption(0).isSelected());
            assertEquals(false, select.getOption(1).isSelected());
            assertEquals(true, select.getOption(2).isSelected());
            assertEquals(false, select.getOption(3).isSelected());
        }
        {
            HtmlSelect select = getC(page1);
            assertEquals(true, select.isMultipleSelectEnabled());
            assertEquals(4, select.getOptionSize());
            assertEquals(false, select.getOption(0).isSelected());
            assertEquals(false, select.getOption(1).isSelected());
            assertEquals(false, select.getOption(2).isSelected());
            assertEquals(false, select.getOption(3).isSelected());

            select.getOption(2).setSelected(true);
            select.getOption(3).setSelected(true);
        }

        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        {
            HtmlSelect select = getA(page1);
            assertEquals(false, select.getOption(0).isSelected());
            assertEquals(true, select.getOption(1).isSelected());
            assertEquals(true, select.getOption(2).isSelected());
        }
        {
            HtmlSelect select = getB(page1);
            assertEquals(false, select.getOption(0).isSelected());
            assertEquals(false, select.getOption(1).isSelected());
            assertEquals(true, select.getOption(2).isSelected());
            assertEquals(false, select.getOption(3).isSelected());
        }
        {
            HtmlSelect select = getC(page1);
            assertEquals(false, select.getOption(0).isSelected());
            assertEquals(false, select.getOption(1).isSelected());
            assertEquals(true, select.getOption(2).isSelected());
            assertEquals(true, select.getOption(3).isSelected());
        }
    }

    private HtmlSelect getA(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSelect) new HtmlUnitXPath("select[1]")
                .selectSingleNode(form);
    }

    private HtmlSelect getB(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSelect) new HtmlUnitXPath("select[2]")
                .selectSingleNode(form);
    }

    private HtmlSelect getC(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSelect) new HtmlUnitXPath("select[3]")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath("//input[@type='submit']")
                .selectSingleNode(form);
    }

}
