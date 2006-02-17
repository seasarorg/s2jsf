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
package org.seasar.jsf.selenium;

import java.io.IOException;

import org.mortbay.http.HttpException;
import org.mortbay.http.HttpFields;
import org.mortbay.http.HttpRequest;
import org.mortbay.http.HttpResponse;

import com.thoughtworks.selenium.embedded.jetty.SeleneseJettyResourceHandler;

/**
 * @author manhole
 */
public class MySeleneseJettyResourceHandler extends
        SeleneseJettyResourceHandler {

    private static final long serialVersionUID = 1L;

    public void handle(String s, String s1, HttpRequest req,
            HttpResponse res) throws HttpException, IOException {
        res.setField(HttpFields.__CacheControl, "no-cache");
        res.setField(HttpFields.__Pragma, "no-cache");
        res.setField(HttpFields.__Expires, "-1");
        super.handle(s, s1, req, res);
    }

}
