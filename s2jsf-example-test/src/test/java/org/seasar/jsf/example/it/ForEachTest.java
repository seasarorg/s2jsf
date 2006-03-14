package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class ForEachTest extends AbstractTestCase {

    public void test1() throws Exception {
        HtmlPage page1 = getPageFromMenu("ForEach");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("ForEach", page1.getTitleText());
        StringAssert.assertContains("to ResultPage", body1);

        // 1

        HtmlForm form1 = getForm(page1);
        System.out.println(form1);

        HtmlTable table1 = (HtmlTable) new HtmlUnitXPath("table")
                .selectSingleNode(form1);
        System.out.println(table1);
        assertEquals(3, table1.getRowCount());
        assertEquals("aaa", table1.getCellAt(1, 1).asText());
        assertEquals("to ResultPage", table1.getCellAt(1, 2).asText());
        HtmlAnchor aaaLink1 = (HtmlAnchor) table1.getCellAt(1, 2)
                .getFirstChild();

        // 2

        HtmlPage page2 = (HtmlPage) aaaLink1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);
        StringAssert.assertContains("Key:111 Name:aaa", body2);

        // 3

        HtmlPage page3 = (HtmlPage) page2.getFirstAnchorByText("previous")
                .click();
        String body3 = getBody(page3).trim();
        System.out.println(body3);

        HtmlTable table3 = (HtmlTable) new HtmlUnitXPath("table")
                .selectSingleNode(getForm(page3));

        assertEquals("222", table3.getCellAt(2, 0).asText());
        HtmlSubmitInput bbbSubmit3 = (HtmlSubmitInput) getFirstChild(table3
                .getCellAt(2, 3), HtmlSubmitInput.class);

        // 4

        HtmlPage page4 = (HtmlPage) bbbSubmit3.click();
        String body4 = getBody(page4).trim();
        System.out.println(body4);
        StringAssert.assertContains("Key:222 Name:bbb", body4);
    }

}
