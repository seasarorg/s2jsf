package org.seasar.jsf.example.it;

import junitx.framework.StringAssert;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public class HelloTest extends AbstractTestCase {

    public void test1() throws Exception {
        HtmlPage page = getPageFromMenu("Hello");
        String body = getBody(page).trim();
        System.out.println(body);

        assertEquals("Hello", page.getTitleText());
        StringAssert.assertContains("Hello World", body);
    }

}
