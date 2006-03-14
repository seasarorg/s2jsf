package org.seasar.jsf.example.it;

import java.net.URL;

import junitx.framework.StringAssert;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MenuTest extends AbstractTestCase {

    public void testMenu() throws Exception {
        URL url = getUrl("");
        System.out.println(url);

        WebClient client = new WebClient();
        HtmlPage page = (HtmlPage) client.getPage(url);
        String body = getBody(page).trim();
        System.out.println(body);

        assertEquals("Home", page.getTitleText());
        StringAssert.assertContains("Welcome to S2JSF Example", body);
    }

}
