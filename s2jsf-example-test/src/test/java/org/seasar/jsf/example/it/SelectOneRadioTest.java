package org.seasar.jsf.example.it;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junitx.framework.StringAssert;

import org.jaxen.JaxenException;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class SelectOneRadioTest extends AbstractTestCase {

    public void testSelectOneRadio() throws Exception {
        // 1

        HtmlPage page1 = getPage();

        {
            HtmlRadioButtonInput[] r = getRadioGroupA(page1);
            assertEquals(4, r.length);
            assertEquals("unchecked", r[0].asText());
            assertEquals("checked", r[1].asText());
            assertEquals("unchecked", r[2].asText());
            assertEquals("unchecked", r[3].asText());

            assertEquals("on", r[0].getValueAttribute());
            assertEquals("1", r[1].getValueAttribute());
            assertEquals("2", r[2].getValueAttribute());
            assertEquals("3", r[3].getValueAttribute());

            assertEquals(true, r[0].isDisabled());
            assertEquals(true, r[1].isDisabled());
            assertEquals(true, r[2].isDisabled());
            assertEquals(true, r[3].isDisabled());

            assertEquals(false, r[0].isChecked());
            assertEquals(true, r[1].isChecked());
            assertEquals(false, r[2].isChecked());
            assertEquals(false, r[3].isChecked());

            r[3].setChecked(true);
        }
        {
            HtmlRadioButtonInput[] r = getRadioGroupB(page1);
            assertEquals(3, r.length);
            System.out.println(r[0]);
            System.out.println(r[1]);
            System.out.println(r[2]);
            assertEquals("unchecked", r[0].asText());
            assertEquals("unchecked", r[1].asText());
            assertEquals("unchecked", r[2].asText());

            assertEquals("1", r[0].getValueAttribute());
            assertEquals("2", r[1].getValueAttribute());
            assertEquals("3", r[2].getValueAttribute());

            assertEquals(false, r[0].isDisabled());
            assertEquals(false, r[1].isDisabled());
            assertEquals(false, r[2].isDisabled());

            assertEquals(false, r[0].isChecked());
            assertEquals(false, r[1].isChecked());
            assertEquals(false, r[2].isChecked());

            r[0].setChecked(true);
        }
        {
            HtmlRadioButtonInput[] r = getRadioGroupC(page1);
            assertEquals(4, r.length);
            assertEquals("unchecked", r[0].asText());
            assertEquals("unchecked", r[1].asText());
            assertEquals("unchecked", r[2].asText());
            assertEquals("unchecked", r[3].asText());

            assertEquals("10", r[0].getValueAttribute());
            assertEquals("20", r[1].getValueAttribute());
            assertEquals("30", r[2].getValueAttribute());
            assertEquals("40", r[3].getValueAttribute());

            assertEquals(false, r[0].isDisabled());
            assertEquals(false, r[1].isDisabled());
            assertEquals(false, r[2].isDisabled());
            assertEquals(false, r[3].isDisabled());

            assertEquals(false, r[0].isChecked());
            assertEquals(false, r[1].isChecked());
            assertEquals(false, r[2].isChecked());
            assertEquals(false, r[3].isChecked());

            r[1].setChecked(true);
        }

        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);

        {
            HtmlRadioButtonInput[] r = getRadioGroupA(page2);
            assertEquals(4, r.length);
            assertEquals("unchecked", r[0].asText());
            assertEquals("checked", r[1].asText());
            assertEquals("unchecked", r[2].asText());
            assertEquals("unchecked", r[3].asText());

            assertEquals("on", r[0].getValueAttribute());
            assertEquals("1", r[1].getValueAttribute());
            assertEquals("2", r[2].getValueAttribute());
            assertEquals("3", r[3].getValueAttribute());

            assertEquals(true, r[0].isDisabled());
            assertEquals(true, r[1].isDisabled());
            assertEquals(true, r[2].isDisabled());
            assertEquals(true, r[3].isDisabled());

            assertEquals(false, r[0].isChecked());
            assertEquals(true, r[1].isChecked());
            assertEquals(false, r[2].isChecked());
            assertEquals(false, r[3].isChecked());

            r[3].setChecked(true);
        }
        {
            HtmlRadioButtonInput[] r = getRadioGroupB(page2);
            assertEquals(3, r.length);
            System.out.println(r[0]);
            System.out.println(r[1]);
            System.out.println(r[2]);
            assertEquals("checked", r[0].asText());
            assertEquals("unchecked", r[1].asText());
            assertEquals("unchecked", r[2].asText());

            assertEquals("1", r[0].getValueAttribute());
            assertEquals("2", r[1].getValueAttribute());
            assertEquals("3", r[2].getValueAttribute());

            assertEquals(false, r[0].isDisabled());
            assertEquals(false, r[1].isDisabled());
            assertEquals(false, r[2].isDisabled());

            assertEquals(true, r[0].isChecked());
            assertEquals(false, r[1].isChecked());
            assertEquals(false, r[2].isChecked());
        }
        {
            HtmlRadioButtonInput[] r = getRadioGroupC(page2);
            assertEquals(4, r.length);
            assertEquals("unchecked", r[0].asText());
            assertEquals("checked", r[1].asText());
            assertEquals("unchecked", r[2].asText());
            assertEquals("unchecked", r[3].asText());

            assertEquals("10", r[0].getValueAttribute());
            assertEquals("20", r[1].getValueAttribute());
            assertEquals("30", r[2].getValueAttribute());
            assertEquals("40", r[3].getValueAttribute());

            assertEquals(false, r[0].isDisabled());
            assertEquals(false, r[1].isDisabled());
            assertEquals(false, r[2].isDisabled());
            assertEquals(false, r[3].isDisabled());

            assertEquals(false, r[0].isChecked());
            assertEquals(true, r[1].isChecked());
            assertEquals(false, r[2].isChecked());
            assertEquals(false, r[3].isChecked());
        }
    }

    public void testValidationError() throws Exception {
        final String errorMessage = "Radio2";

        // 1

        HtmlPage page1 = getPage();
        String body1 = getBody(page1).trim();
        StringAssert.assertNotContains(errorMessage, body1);
        HtmlSubmitInput submit1 = getSubmit(page1);

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        String body2 = getBody(page2).trim();
        System.out.println(body2);
        StringAssert.assertContains(errorMessage, body2);
    }

    private HtmlPage getPage() throws MalformedURLException, IOException,
            UnsupportedEncodingException {
        HtmlPage page = getPageFromMenu("SelectOneRadio");
        String body = getBody(page).trim();
        System.out.println(body);

        assertEquals("SelectOneRadio", page.getTitleText());
        return page;
    }

    private HtmlRadioButtonInput[] getRadioGroupA(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        String id = form.getId();
        List l = new HtmlUnitXPath(".//input[@type='radio'][@name='" + id
                + ":radio1']").selectNodes(form);
        HtmlRadioButtonInput[] radios = new HtmlRadioButtonInput[l.size()];
        l.toArray(radios);
        return radios;
    }

    private HtmlRadioButtonInput[] getRadioGroupB(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        String id = form.getId();
        List l = new HtmlUnitXPath(".//input[@type='radio'][@name='" + id
                + ":radio2']").selectNodes(form);
        HtmlRadioButtonInput[] radios = new HtmlRadioButtonInput[l.size()];
        l.toArray(radios);
        return radios;
    }

    private HtmlRadioButtonInput[] getRadioGroupC(HtmlPage page)
            throws JaxenException {
        HtmlForm form = getForm(page);
        List l = new HtmlUnitXPath(".//input[@type='radio']").selectNodes(form);
        List radioList = new ArrayList();
        for (Iterator it = l.iterator(); it.hasNext();) {
            HtmlRadioButtonInput radio = (HtmlRadioButtonInput) it.next();
            if (-1 == radio.getNameAttribute().indexOf("radio")) {
                radioList.add(radio);
            }
        }

        HtmlRadioButtonInput[] radios = new HtmlRadioButtonInput[radioList
                .size()];
        radioList.toArray(radios);
        return radios;
    }

    private HtmlSubmitInput getSubmit(HtmlPage page) throws JaxenException {
        HtmlForm form = getForm(page);
        return (HtmlSubmitInput) new HtmlUnitXPath(".//input[@type='submit']")
                .selectSingleNode(form);
    }

}
