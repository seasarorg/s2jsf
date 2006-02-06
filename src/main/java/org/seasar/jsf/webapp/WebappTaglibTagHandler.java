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
package org.seasar.jsf.webapp;

import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author manhole
 */
public class WebappTaglibTagHandler extends TagHandler {

    private static final long serialVersionUID = 1L;

    public void start(TagHandlerContext context, Attributes attributes) {
        context.push(new Taglib());
    }

    public void end(TagHandlerContext context, String body) {
        Taglib servlet = (Taglib) context.pop();
        WebappConfig webappConfig = (WebappConfig) context.peek();
        webappConfig.addTaglib(servlet);
    }

    public static class TaglibUriTagHandler extends TagHandler {
        private static final long serialVersionUID = 1L;

        public void end(TagHandlerContext context, String body) {
            Taglib taglib = (Taglib) context.peek();
            taglib.setTaglibUri(body);
        }
    }

    public static class TaglibLocationTagHandler extends TagHandler {
        private static final long serialVersionUID = 1L;

        public void end(TagHandlerContext context, String body) {
            Taglib taglib = (Taglib) context.peek();
            taglib.setTaglibLocation(body);
        }
    }

}
