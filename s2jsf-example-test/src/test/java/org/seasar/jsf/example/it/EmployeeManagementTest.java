package org.seasar.jsf.example.it;

import java.io.IOException;
import java.net.MalformedURLException;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class EmployeeManagementTest extends AbstractTestCase {

    public void test1() throws Exception {
        HtmlPage page1 = getPage();

        // 1

        HtmlSubmitInput create = getCreateButton(page1);

        {
            HtmlTable table = getFirstTable(page1);
            assertEquals(7, table.getRowCount());
        }

        // 2: create page

        HtmlPage page2 = (HtmlPage) create.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        StringAssert.assertContains("Create", body2);

        {
            HtmlTable table = getFirstTable(page2);
            assertEquals(8, table.getRowCount());
            HtmlTextInput employeeNo = (HtmlTextInput) getFirstChild(table
                    .getCellAt(0, 1), HtmlTextInput.class);
            HtmlTextInput employeeName = (HtmlTextInput) getFirstChild(table
                    .getCellAt(1, 1), HtmlTextInput.class);
            HtmlTextInput job = (HtmlTextInput) getFirstChild(table.getCellAt(
                    2, 1), HtmlTextInput.class);
            HtmlTextInput manager = (HtmlTextInput) getFirstChild(table
                    .getCellAt(3, 1), HtmlTextInput.class);
            HtmlTextInput hireDate = (HtmlTextInput) getFirstChild(table
                    .getCellAt(4, 1), HtmlTextInput.class);
            HtmlTextInput salary = (HtmlTextInput) getFirstChild(table
                    .getCellAt(5, 1), HtmlTextInput.class);
            HtmlTextInput commission = (HtmlTextInput) getFirstChild(table
                    .getCellAt(6, 1), HtmlTextInput.class);
            HtmlSelect department = (HtmlSelect) getFirstChild(table.getCellAt(
                    7, 1), HtmlSelect.class);

            employeeNo.setValueAttribute("7650");
            employeeName.setValueAttribute("Foo Name");
            job.setValueAttribute("deployer");
            manager.setValueAttribute("7902");
            hireDate.setValueAttribute("1977/10/05");
            salary.setValueAttribute("9999");
            commission.setValueAttribute("1234");
            // 40:OPERATIONS
            department.setSelectedAttribute("40", true);
        }

        HtmlSubmitInput confirm2 = getConfirmButton(page2);

        // 3: create confirm page

        HtmlPage page3 = (HtmlPage) confirm2.click();
        String body3 = getBody(page3).trim();
        System.out.println(body3);

        {
            HtmlTable table = getFirstTable(page3);
            assertEquals(8, table.getRowCount());

            assertEquals("7650", table.getCellAt(0, 1).asText());
            assertEquals("Foo Name", table.getCellAt(1, 1).asText());
            assertEquals("deployer", table.getCellAt(2, 1).asText());
            assertEquals("7902", table.getCellAt(3, 1).asText());
            assertEquals("1977/10/05", table.getCellAt(4, 1).asText());
            assertEquals("9999", table.getCellAt(5, 1).asText());
            assertEquals("1234", table.getCellAt(6, 1).asText());
            assertEquals("OPERATIONS", table.getCellAt(7, 1).asText());
        }

        HtmlSubmitInput store3 = getStoreButton(page3);

        // 4: first page

        HtmlPage page4 = (HtmlPage) store3.click();
        String body4 = getBody(page4).trim();
        System.out.println(body4);

        {
            HtmlTable table = getFirstTable(page4);
            assertEquals(7, table.getRowCount());
            HtmlTextInput employeeNo = (HtmlTextInput) getFirstChild(table
                    .getCellAt(0, 1), HtmlTextInput.class);
            employeeNo.setValueAttribute("7650");
        }

        HtmlSubmitInput search4 = getSearchButton(page4);

        // 5: search result page

        HtmlPage page5 = (HtmlPage) search4.click();
        String body5 = getBody(page5).trim();
        System.out.println(body5);

        {
            HtmlTable table = getFirstTable(page5);
            assertEquals(2, table.getRowCount());

            assertEquals("Foo Name", table.getCellAt(1, 1).asText());
        }

        HtmlAnchor delete5 = page5.getFirstAnchorByText("Delete");

        // 6: delete confirm page

        HtmlPage page6 = (HtmlPage) delete5.click();
        String body6 = getBody(page6).trim();
        System.out.println(body6);

        HtmlSubmitInput store6 = getStoreButton(page6);

        // 7: first page

        HtmlPage page7 = (HtmlPage) store6.click();
        String body7 = getBody(page7).trim();
        System.out.println(body7);

        {
            HtmlTable table = getFirstTable(page7);
            HtmlTextInput employeeNo = (HtmlTextInput) getFirstChild(table
                    .getCellAt(0, 1), HtmlTextInput.class);
            assertEquals("7650", employeeNo.getValueAttribute());
            employeeNo.setValueAttribute("");
        }

        HtmlSubmitInput search7 = getSearchButton(page7);

        // 8: search result

        HtmlPage page8 = (HtmlPage) search7.click();
        String body8 = getBody(page8).trim();
        System.out.println(body8);

        {
            HtmlTable table = getFirstTable(page8);
            assertEquals(15, table.getRowCount());
        }
    }

    private HtmlPage getPage() throws MalformedURLException, IOException {
        HtmlPage page1 = getPageFromMenu("Employee Management");
        String body1 = getBody(page1).trim();
        System.out.println(body1);

        assertEquals("Employee Management", page1.getTitleText());
        StringAssert.assertContains("EmployeeNo", body1);
        return page1;
    }

    public void test2() throws Exception {
        HtmlPage page1 = getPage();
        HtmlSubmitInput create = getCreateButton(page1);

        // 2: create page

        HtmlPage page2 = (HtmlPage) create.click();
        String body2 = getBody(page2).trim();
        StringAssert.assertContains("Create", body2);
        HtmlSubmitInput previous = getPreviousButton(page2);

        // 3: first page

        HtmlPage page3 = (HtmlPage) previous.click();
        String body3 = getBody(page3).trim();
        StringAssert.assertNotContains("Create", body3);
    }

    private HtmlTable getFirstTable(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        HtmlTable table = (HtmlTable) new HtmlUnitXPath(".//table")
                .selectSingleNode(form);
        return table;
    }

    private HtmlSubmitInput getCreateButton(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(
                ".//input[@type='submit'][@value='create']")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getConfirmButton(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(
                ".//input[@type='submit'][@value='confirm']")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getStoreButton(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(
                ".//input[@type='submit'][@value='store']")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getSearchButton(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(
                ".//input[@type='submit'][@value='search']")
                .selectSingleNode(form);
    }

    private HtmlSubmitInput getPreviousButton(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(
                ".//input[@type='submit'][@value='previous']")
                .selectSingleNode(form);
    }

}
