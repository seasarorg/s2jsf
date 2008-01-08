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
package org.seasar.jsf.util;

import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.servlet.http.HttpServletRequest;

/**
 * @author higa
 * 
 */
public class UIParameterUtil {

    private UIParameterUtil() {
    }

    public static void saveParamsToRequest(UICommand command,
            HttpServletRequest request) {
        List children = command.getChildren();
        for (int i = 0; i < children.size(); ++i) {
            UIComponent child = (UIComponent) children.get(i);
            if (child instanceof UIParameter) {
                UIParameter param = (UIParameter) child;
                Object value = request.getParameter(param.getName());
                if (value != null || "".equals(value)) {
                    request.setAttribute(param.getName(), value);
                } else {
                    request.setAttribute(param.getName(), param.getValue());
                }
            }
        }
    }
}