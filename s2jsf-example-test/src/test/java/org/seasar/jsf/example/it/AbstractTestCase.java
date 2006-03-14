/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.jsf.example.it;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import junit.framework.TestCase;

import org.jaxen.JaxenException;
import org.seasar.framework.util.InputStreamReaderUtil;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.framework.util.ResourceNotFoundRuntimeException;
import org.seasar.framework.util.ResourceUtil;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public abstract class AbstractTestCase extends TestCase {

    private static int port_ = 8080;

    private static String baseUrl_ = "http://localhost:" + port_
            + "/s2jsf-example/";

    protected URL getUrl(String path) throws MalformedURLException {
        return new URL(baseUrl_ + path);
    }

    protected String getBody(HtmlPage page) throws UnsupportedEncodingException {
        WebResponse webResponse = page.getWebResponse();
        String body = new String(webResponse.getResponseBody(), page
                .getPageEncoding());
        return body;
    }

    protected String readText(String s) {
        String pathByClass = getClass().getName().replace('.', '/') + "_" + s;
        InputStream is = null;
        try {
            is = ResourceUtil.getResourceAsStream(pathByClass);
        } catch (ResourceNotFoundRuntimeException e) {
            String pathByPackage = getClass().getPackage().getName().replace(
                    '.', '/')
                    + "/" + s;
            is = ResourceUtil.getResourceAsStream(pathByPackage);
        }
        Reader reader = InputStreamReaderUtil.create(is, "UTF-8");
        return ReaderUtil.readText(reader);
    }

    protected URL getFileAsUrl(String s) {
        String fileNameByClass = getClass().getName().replace('.', '/') + "_"
                + s;
        try {
            return ResourceUtil.getResource(fileNameByClass);
        } catch (ResourceNotFoundRuntimeException e) {
            String fileNameByPackage = getClass().getPackage().getName()
                    .replace('.', '/')
                    + "/" + s;
            return ResourceUtil.getResource(fileNameByPackage);
        }
    }

    protected HtmlPage getPageFromMenu(String linkText)
            throws MalformedURLException, IOException {
        URL indexUrl = getUrl("");

        WebClient client = new WebClient();
        HtmlPage menuPage = (HtmlPage) client.getPage(indexUrl);
        HtmlAnchor helloLink = menuPage.getFirstAnchorByText(linkText);
        HtmlPage page = (HtmlPage) helloLink.click();
        return page;
    }

    protected HtmlForm getForm(HtmlPage page) throws JaxenException {
        return (HtmlForm) new HtmlUnitXPath("//form[1]").selectNodes(page).get(
                1);
    }

    protected HtmlElement getFirstChild(DomNode node, Class type) {
        if (!HtmlElement.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException();
        }
        for (Iterator it = node.getChildIterator(); it.hasNext();) {
            Object obj = it.next();
            if (type.isAssignableFrom(obj.getClass())) {
                return (HtmlElement) obj;
            }
        }
        throw new IllegalStateException();
    }

}
