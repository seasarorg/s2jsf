package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class ForEach3Test extends AbstractTestCase {

    public void testForEach3() throws Exception {
        HtmlPage page1 = getPageFromMenu("ForEach3");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("ForEach3", page1.getTitleText());
        StringAssert.assertContains("submit", body1);

        // 1

        {
            HtmlTable table = getTable(page1);
            assertEquals(2, table.getRowCount());

            HtmlTextInput a1 = getA(table);
            HtmlTextInput b1 = getB(table);
            HtmlTextInput c1 = getC(table);
            HtmlTextInput d1 = getD(table);
            assertEquals("11", a1.getValueAttribute());
            assertEquals("12", b1.getValueAttribute());
            assertEquals("21", c1.getValueAttribute());
            assertEquals("22", d1.getValueAttribute());

            a1.setValueAttribute("11a");
            b1.setValueAttribute("12b");
            c1.setValueAttribute("21c");
            d1.setValueAttribute("22d");
        }

        HtmlSubmitInput submit1 = getSubmit(page1);
        System.out.println(submit1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        {
            HtmlTable table = getTable(page2);
            assertEquals(2, table.getRowCount());
            HtmlTextInput a2 = getA(table);
            HtmlTextInput b2 = getB(table);
            HtmlTextInput c2 = getC(table);
            HtmlTextInput d2 = getD(table);
            assertEquals("11a", a2.getValueAttribute());
            assertEquals("12b", b2.getValueAttribute());
            assertEquals("21c", c2.getValueAttribute());
            assertEquals("22d", d2.getValueAttribute());
        }
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form1 = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(".//input[@type='submit']")
                .selectSingleNode(form1);
    }

    private HtmlTable getTable(HtmlPage page) throws JaxenException {
        HtmlForm form1 = getForm(page);
        return (HtmlTable) new HtmlUnitXPath("table").selectSingleNode(form1);
    }

    public void test2() throws Exception {
        testForEach3();
    }

    private HtmlTextInput getA(HtmlTable table1) {
        return (HtmlTextInput) getFirstChild(table1.getCellAt(0, 0),
                HtmlTextInput.class);
    }

    private HtmlTextInput getB(HtmlTable table1) {
        return (HtmlTextInput) getFirstChild(table1.getCellAt(0, 1),
                HtmlTextInput.class);
    }

    private HtmlTextInput getC(HtmlTable table1) {
        return (HtmlTextInput) getFirstChild(table1.getCellAt(1, 0),
                HtmlTextInput.class);
    }

    private HtmlTextInput getD(HtmlTable table1) {
        return (HtmlTextInput) getFirstChild(table1.getCellAt(1, 1),
                HtmlTextInput.class);
    }

}
