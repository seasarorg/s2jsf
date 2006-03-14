package org.seasar.jsf.example.it;

import java.util.List;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class SelectManyCheckboxTest extends AbstractTestCase {

    public void test1() throws Exception {
        HtmlPage page1 = getPageFromMenu("SelectManyCheckbox");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("SelectManyCheckbox", page1.getTitleText());
        StringAssert.assertContains("OPERATIONS", body1);

        // 1

        {
            HtmlCheckBoxInput[] group = getCheckboxGroupA(page1);
            assertEquals(3, group.length);
            assertEquals(false, group[0].isChecked());
            assertEquals(true, group[1].isChecked());
            assertEquals(false, group[2].isChecked());

            group[0].setChecked(true);
            assertEquals(true, group[0].isChecked());

            assertEquals(false, group[0].isDefaultChecked());
            assertEquals(true, group[1].isDefaultChecked());
            assertEquals(false, group[2].isDefaultChecked());
        }
        {
            HtmlCheckBoxInput[] group = getCheckboxGroupB(page1);
            assertEquals(4, group.length);
            group[0].setChecked(true);
            group[1].setChecked(true);
            group[2].setChecked(true);
            group[3].setChecked(true);
        }
        {
            HtmlCheckBoxInput[] group = getCheckboxGroupC(page1);
            assertEquals(4, group.length);
            group[0].setChecked(false);
            group[1].setChecked(false);
            group[2].setChecked(false);
            group[3].setChecked(false);
        }

        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        {
            HtmlCheckBoxInput[] group = getCheckboxGroupA(page1);
            assertEquals(3, group.length);
            assertEquals(true, group[0].isChecked());
            assertEquals(true, group[1].isChecked());
            assertEquals(false, group[2].isChecked());
        }
        {
            HtmlCheckBoxInput[] group = getCheckboxGroupB(page1);
            assertEquals(4, group.length);
            assertEquals(true, group[0].isChecked());
            assertEquals(true, group[1].isChecked());
            assertEquals(true, group[2].isChecked());
            assertEquals(true, group[3].isChecked());
        }
        {
            HtmlCheckBoxInput[] group = getCheckboxGroupC(page1);
            assertEquals(4, group.length);
            assertEquals(false, group[0].isChecked());
            assertEquals(false, group[1].isChecked());
            assertEquals(false, group[2].isChecked());
            assertEquals(false, group[3].isChecked());
        }
    }

    private HtmlCheckBoxInput[] getCheckboxGroupA(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        HtmlTable table = (HtmlTable) new HtmlUnitXPath("table[1]")
                .selectSingleNode(form);
        HtmlCheckBoxInput[] checkboxes = extractDecendantCheckbox(table);
        return checkboxes;
    }

    private HtmlCheckBoxInput[] getCheckboxGroupB(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        HtmlTable table = (HtmlTable) new HtmlUnitXPath("table[2]")
                .selectSingleNode(form);
        HtmlCheckBoxInput[] checkboxes = extractDecendantCheckbox(table);
        return checkboxes;
    }

    private HtmlCheckBoxInput[] getCheckboxGroupC(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        HtmlTable table = (HtmlTable) new HtmlUnitXPath("table[3]")
                .selectSingleNode(form);
        HtmlCheckBoxInput[] checkboxes = extractDecendantCheckbox(table);
        return checkboxes;
    }

    private HtmlCheckBoxInput[] extractDecendantCheckbox(HtmlTable table)
            throws JaxenException {
        List nodes = new HtmlUnitXPath(".//input[@type='checkbox']")
                .selectNodes(table);
        HtmlCheckBoxInput[] checkboxes = new HtmlCheckBoxInput[nodes.size()];
        nodes.toArray(checkboxes);
        return checkboxes;
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath("//input[@type='submit']")
                .selectSingleNode(form);
    }

}
