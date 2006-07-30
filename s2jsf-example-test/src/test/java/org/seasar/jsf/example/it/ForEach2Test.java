package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class ForEach2Test extends AbstractTestCase {

    public void testForEach2() throws Exception {
        HtmlPage page1 = getPageFromMenu("ForEach2");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("ForEach2", page1.getTitleText());
        StringAssert.assertContains("delete", body1);

        // 1

        {
            HtmlTable table = getTable(page1);
            assertEquals(6, table.getRowCount());

            HtmlTextInput input = (HtmlTextInput) getFirstChild(table
                    .getCellAt(2, 1), HtmlTextInput.class);
            assertEquals("bbb", input.getValueAttribute());

            ((HtmlCheckBoxInput) getFirstChild(table.getCellAt(1, 0),
                    HtmlCheckBoxInput.class)).setChecked(true);
            ((HtmlCheckBoxInput) getFirstChild(table.getCellAt(2, 0),
                    HtmlCheckBoxInput.class)).setChecked(true);
            ((HtmlCheckBoxInput) getFirstChild(table.getCellAt(3, 0),
                    HtmlCheckBoxInput.class)).setChecked(true);

            // Dynamic tabindex
            final int rowCount = table.getRowCount();
            for (int i = 1; i < rowCount; i++) {
                input = (HtmlTextInput) getFirstChild(table.getCellAt(i, 1),
                        HtmlTextInput.class);
                String tabindex = input.getAttributeValue("tabindex");
                assertEquals(tabindex, String.valueOf(i - 1));
            }

        }

        HtmlSubmitInput submit1 = getUpdateButton(page1);
        System.out.println(submit1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        {
            HtmlTable table = getTable(page2);
            assertEquals(3, table.getRowCount());

            HtmlTextInput input2 = (HtmlTextInput) getFirstChild(table
                    .getCellAt(1, 1), HtmlTextInput.class);
            assertEquals("ddd", input2.getValueAttribute());
            input2.setValueAttribute("abcde");
        }
        HtmlSubmitInput submit2 = getUpdateButton(page2);

        // 3

        HtmlPage page3 = (HtmlPage) submit2.click();
        String body3 = getBody(page3).trim();
        System.out.println(body3);

        {
            HtmlTable table3 = getTable(page3);
            HtmlTextInput input3 = (HtmlTextInput) getFirstChild(table3
                    .getCellAt(1, 1), HtmlTextInput.class);
            assertEquals("abcde", input3.getValueAttribute());
        }

        HtmlSubmitInput submit3 = getAddRowBottun(page3);

        // 4

        HtmlPage page4 = (HtmlPage) submit3.click();
        HtmlTable table4 = getTable(page4);
        assertEquals(4, table4.getRowCount());
    }

    private HtmlSubmitInput getUpdateButton(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(
                ".//input[@type='submit'][@value='update']")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getAddRowBottun(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(
                ".//input[@type='submit'][@value='add row']")
                .selectSingleNode(form);
    }

    private HtmlTable getTable(HtmlPage page) throws JaxenException {
        HtmlForm form1 = getForm(page);
        return (HtmlTable) new HtmlUnitXPath("table").selectSingleNode(form1);
    }

    public void test2() throws Exception {
        testForEach2();
    }

}
