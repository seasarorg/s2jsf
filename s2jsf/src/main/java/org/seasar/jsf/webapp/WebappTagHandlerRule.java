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

import org.seasar.framework.xml.TagHandlerRule;

/**
 * @author manhole
 */
public class WebappTagHandlerRule extends TagHandlerRule {

    private static final long serialVersionUID = 1L;

    public WebappTagHandlerRule() {
        addTagHandler("/web-app", new WebappTagHandler());

        // context-param
        addTagHandler("/web-app/context-param",
                new WebappContextParamTagHandler());
        addTagHandler("/web-app/context-param/param-name",
                new WebappContextParamTagHandler.ParamNameTagHandler());
        addTagHandler("/web-app/context-param/param-value",
                new WebappContextParamTagHandler.ParamValueTagHandler());

        // servlet
        addTagHandler("/web-app/servlet", new WebappServletTagHandler());
        addTagHandler("/web-app/servlet/servlet-name",
                new WebappServletTagHandler.ServletNameTagHandler());
        addTagHandler("/web-app/servlet/servlet-class",
                new WebappServletTagHandler.ServletClassTagHandler());
        addTagHandler("/web-app/servlet/load-on-startup",
                new WebappServletTagHandler.LoadOnStartupTagHandler());
        addTagHandler("/web-app/servlet/init-param",
                new WebappServletTagHandler.InitParamTagHandler());
        addTagHandler("/web-app/servlet/init-param/param-name",
                new WebappServletTagHandler.InitParamNameTagHandler());
        addTagHandler("/web-app/servlet/init-param/param-value",
                new WebappServletTagHandler.InitParamValueTagHandler());

        // taglib
        addTagHandler("/web-app/taglib", new WebappTaglibTagHandler());
        addTagHandler("/web-app/taglib/taglib-uri",
                new WebappTaglibTagHandler.TaglibUriTagHandler());
        addTagHandler("/web-app/taglib/taglib-location",
                new WebappTaglibTagHandler.TaglibLocationTagHandler());
    }

}
