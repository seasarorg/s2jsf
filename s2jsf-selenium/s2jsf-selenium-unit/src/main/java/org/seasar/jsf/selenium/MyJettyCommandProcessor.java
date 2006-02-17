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

import java.io.File;
import java.io.IOException;

import org.mortbay.http.HttpContext;
import org.mortbay.http.HttpRequest;
import org.mortbay.http.HttpResponse;
import org.mortbay.http.RequestLog;
import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.util.InetAddrPort;
import org.mortbay.util.MultiException;
import org.seasar.framework.exception.IORuntimeException;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.embedded.jetty.NullStaticContentHandler;
import com.thoughtworks.selenium.embedded.jetty.SeleneseJettyResourceHandler;

public class MyJettyCommandProcessor implements CommandProcessor {

    private Server _server;

    private SeleneseJettyResourceHandler _seleneseJettyResourceHandler = new MySeleneseJettyResourceHandler();

    private static final int DEFAULT_PORT = 8080;

    private int _port = DEFAULT_PORT;

    public MyJettyCommandProcessor() {
        configureServer();
    }

    public void deploySeleniumDriver(File seleniumAppRoot) {
        try {
            WebApplicationContext context = new WebApplicationContext(
                    seleniumAppRoot.getCanonicalPath());
            new NullStaticContentHandler().addStaticContent(context);
            context.addHandler(_seleneseJettyResourceHandler);
            context.setContextPath("/selenium-driver");
            _server.addContext(context);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public void addWebApplication(File webAppRoot) {
        try {
            WebApplicationContext context = new WebApplicationContext(
                    webAppRoot.getCanonicalPath());
            context.setContextPath("/");
            _server.addContext(context);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private void configureServer() {
        _server = new Server();
        try {
            SocketListener listener = new SocketListener(new InetAddrPort(
                    "localhost", _port));
            listener.setMaxIdleTimeMs(1000 * 300); // 効いていない?
            _server.addListener(listener);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        _server.setRequestLog(new MyRequestLog());
    }

    public String doCommand(String command, String field, String value) {
        return _seleneseJettyResourceHandler.doCommand(command, field, value);
    }

    public void start() {
        try {
            _server.start();
            System.out.println("********** Jetty Start **********");
            HttpContext[] contexts = _server.getContexts();
            for (int i = 0; i < contexts.length; i++) {
                HttpContext context = contexts[i];
                System.out.println("  context=" + context);
                System.out.println("    classpth=" + context.getClassPath());
                System.out.println("    baseResource="
                        + context.getBaseResource());
                // System.out.println(" attributes=" + context.getAttributes());
                System.out.println("    contextPath="
                        + context.getContextPath());
            }

        } catch (MultiException e) {
            throw new MultiRuntimeException(
                    "Exception starting Jetty. Port blocked by another process?",
                    e);
        }
    }

    public void stop() {
        try {
            System.out.println("********** Jetty Stop **********");
            _server.stop();
            System.out.println("Stopped.");
        } catch (InterruptedException e) {
            throw new InterruptedRuntimeException(
                    "Jetty Interrupted during stop", e);
        }
    }

    public Server getServer() {
        return _server;
    }

    private static class MyRequestLog implements RequestLog {

        private static final long serialVersionUID = 1L;

        public void log(HttpRequest httprequest, HttpResponse httpresponse,
                int j) {
        }

        public void start() throws Exception {
        }

        public void stop() {
        }

        public boolean isStarted() {
            return false;
        }
    }

}
