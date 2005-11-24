/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.jsf.util;

import org.apache.myfaces.renderkit.html.util.JavascriptUtils;

public class JavascriptUtil {

    private static final String AUTO_SCROLL_PARAM = "autoScroll";
    private static final String AUTO_SCROLL_FUNCTION = "getScrolling()";

    public static String getClearHiddenCommandFormParamsFunctionName(
        String formName) {
        return "clear_"
            + JavascriptUtils.getValidJavascriptName(formName, false);
    }

    public static void appendAutoScrollAssignment(StringBuffer onClickValue,
        String formName) {
        onClickValue.append("document.forms['").append(formName).append("']");
        onClickValue.append(".elements['").append(AUTO_SCROLL_PARAM).append(
            "']");
        onClickValue.append(".value=").append(AUTO_SCROLL_FUNCTION).append(";");
    }

}
