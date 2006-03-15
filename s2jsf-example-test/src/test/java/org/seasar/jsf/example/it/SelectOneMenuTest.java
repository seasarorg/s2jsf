package org.seasar.jsf.example.it;

import java.util.List;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class SelectOneMenuTest extends AbstractTestCase {

    public void test1() throws Exception {
        HtmlPage page1 = getPageFromMenu("SelectOneMenu");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("SelectOneMenu", page1.getTitleText());
        StringAssert.assertContains("Aaa:", body1);

        // 1

        {
            HtmlSelect select = getA(page1);
            assertEquals(false, select.isMultipleSelectEnabled());
            assertEquals(4, select.getOptionSize());

            HtmlOption opt1 = select.getOption(0);
            assertEquals("Please select", opt1.asText());
            assertEquals("", opt1.getValueAttribute());
            HtmlOption opt2 = select.getOption(1);
            assertEquals("One", opt2.asText());
            assertEquals("1", opt2.getValueAttribute());
            HtmlOption opt3 = select.getOption(2);
            assertEquals("Two", opt3.asText());
            assertEquals("2", opt3.getValueAttribute());
            HtmlOption opt4 = select.getOption(3);
            assertEquals("Three", opt4.asText());
            assertEquals("3", opt4.getValueAttribute());

            opt3.setSelected(true);
        }
        {
            HtmlSelect select = getB(page1);
            assertEquals(false, select.isMultipleSelectEnabled());
            assertEquals(5, select.getOptionSize());
            List options = select.getOptions();
            HtmlOption opt = (HtmlOption) options.get(4);
            opt.setSelected(true);
        }
        {
            HtmlSelect select = getC(page1);
            assertEquals(false, select.isMultipleSelectEnabled());
            assertEquals(5, select.getOptionSize());
            HtmlOption opt = select.getOptionByValue("30");
            opt.setSelected(true);
        }

        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        {
            HtmlSelect select = getA(page2);
            assertEquals(4, select.getOptionSize());
            assertEquals(false, select.getOption(0).isSelected());
            assertEquals(false, select.getOption(1).isSelected());
            assertEquals(true, select.getOption(2).isSelected());
            assertEquals(false, select.getOption(3).isSelected());
        }
        {
            HtmlSelect select = getB(page2);
            assertEquals(5, select.getOptionSize());
            assertEquals(false, select.getOption(0).isSelected());
            assertEquals(false, select.getOption(1).isSelected());
            assertEquals(false, select.getOption(2).isSelected());
            assertEquals(false, select.getOption(3).isSelected());
            assertEquals(true, select.getOption(4).isSelected());
        }
        {
            HtmlSelect select = getC(page2);
            assertEquals(5, select.getOptionSize());
            assertEquals(false, select.getOption(0).isSelected());
            assertEquals(false, select.getOption(1).isSelected());
            assertEquals(false, select.getOption(2).isSelected());
            assertEquals(true, select.getOption(3).isSelected());
            assertEquals(false, select.getOption(4).isSelected());
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
        return (HtmlSubmitInput) new HtmlUnitXPath(".//input[@type='submit']")
                .selectSingleNode(form);
    }

}
