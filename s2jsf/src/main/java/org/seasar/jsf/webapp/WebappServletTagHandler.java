/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.jsf.webapp;

import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author manhole
 */
public class WebappServletTagHandler extends TagHandler {

    private static final long serialVersionUID = 1L;

    public void start(TagHandlerContext context, Attributes attributes) {
        context.push(new Servlet());
    }

    public void end(TagHandlerContext context, String body) {
        Servlet servlet = (Servlet) context.pop();
        WebappConfig webappConfig = (WebappConfig) context.peek();
        webappConfig.addServlet(servlet);
    }

    public static class ServletNameTagHandler extends TagHandler {
        private static final long serialVersionUID = 1L;

        public void end(TagHandlerContext context, String body) {
            Servlet servlet = (Servlet) context.peek();
            servlet.setServletName(body);
        }
    }

    public static class ServletClassTagHandler extends TagHandler {
        private static final long serialVersionUID = 1L;

        public void end(TagHandlerContext context, String body) {
            Servlet servlet = (Servlet) context.peek();
            servlet.setServletClass(body);
        }
    }

    public static class LoadOnStartupTagHandler extends TagHandler {
        private static final long serialVersionUID = 1L;

        public void end(TagHandlerContext context, String body) {
            Servlet servlet = (Servlet) context.peek();
            servlet.setLoadOnStartup(body);
        }
    }

    public static class InitParamTagHandler extends TagHandler {
        private static final long serialVersionUID = 1L;

        public void start(TagHandlerContext context, Attributes attributes) {
            context.push(new InitParam());
        }

        public void end(TagHandlerContext context, String body) {
            InitParam initParam = (InitParam) context.pop();
            Servlet servlet = (Servlet) context.peek();
            servlet.addInitParam(initParam);
        }
    }

    public static class InitParamNameTagHandler extends TagHandler {
        private static final long serialVersionUID = 1L;

        public void end(TagHandlerContext context, String body) {
            InitParam initParam = (InitParam) context.peek();
            initParam.setParamName(body);
        }
    }

    public static class InitParamValueTagHandler extends TagHandler {
        private static final long serialVersionUID = 1L;

        public void end(TagHandlerContext context, String body) {
            InitParam initParam = (InitParam) context.peek();
            initParam.setParamValue(body);
        }
    }

}
