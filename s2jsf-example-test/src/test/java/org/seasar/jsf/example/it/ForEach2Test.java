package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

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

    public void test1() throws Exception {
        HtmlPage page1 = getPageFromMenu("ForEach2");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("ForEach2", page1.getTitleText());
        StringAssert.assertContains("delete", body1);

        // 1

        HtmlForm form1 = getForm(page1);

        HtmlTable table1 = (HtmlTable) new HtmlUnitXPath("table")
                .selectSingleNode(form1);
        System.out.println(table1);
        assertEquals(6, table1.getRowCount());

        ((HtmlCheckBoxInput) getFirstChild(table1.getCellAt(1, 0),
                HtmlCheckBoxInput.class)).setChecked(true);
        ((HtmlCheckBoxInput) getFirstChild(table1.getCellAt(2, 0),
                HtmlCheckBoxInput.class)).setChecked(true);
        ((HtmlCheckBoxInput) getFirstChild(table1.getCellAt(3, 0),
                HtmlCheckBoxInput.class)).setChecked(true);

        HtmlSubmitInput submit1 = (HtmlSubmitInput) new HtmlUnitXPath(
                "//input[@type='submit'][@value='update']")
                .selectSingleNode(form1);
        System.out.println(submit1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        HtmlForm form2 = getForm(page2);
        HtmlTable table2 = (HtmlTable) new HtmlUnitXPath("table")
                .selectSingleNode(form2);
        assertEquals(3, table2.getRowCount());

        HtmlTextInput input2 = (HtmlTextInput) getFirstChild(table2.getCellAt(
                1, 1), HtmlTextInput.class);
        assertEquals("ddd", input2.getValueAttribute());
        input2.setValueAttribute("abcde");
        HtmlSubmitInput submit2 = (HtmlSubmitInput) new HtmlUnitXPath(
                "//input[@type='submit'][@value='update']")
                .selectSingleNode(form2);

        // 3

        HtmlPage page3 = (HtmlPage) submit2.click();
        String body3 = getBody(page3).trim();
        System.out.println(body3);

        HtmlForm form3 = getForm(page3);
        HtmlTable table3 = (HtmlTable) new HtmlUnitXPath("table")
                .selectSingleNode(form3);
        HtmlTextInput input3 = (HtmlTextInput) getFirstChild(table3.getCellAt(
                1, 1), HtmlTextInput.class);
        assertEquals("abcde", input3.getValueAttribute());

        HtmlSubmitInput submit3 = (HtmlSubmitInput) new HtmlUnitXPath(
                "//input[@type='submit'][@value='add row']")
                .selectSingleNode(form3);

        // 4

        HtmlPage page4 = (HtmlPage) submit3.click();
        HtmlForm form4 = getForm(page4);
        HtmlTable table4 = (HtmlTable) new HtmlUnitXPath("table")
                .selectSingleNode(form4);
        assertEquals(4, table4.getRowCount());
    }

}
