/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.jsf.JsfConstants;

/**
 * @author manhole
 */
public class HtmlFormRendererUtil {

    private HtmlFormRendererUtil() {
    }

    public static String createFormIdentifyKey(FacesContext facesContext,
            UIForm form) {
        UIViewRoot viewRoot = facesContext.getViewRoot();
        String viewId = viewRoot.getViewId();
        String formIdentifyKey = form.getClientId(facesContext) + "_" + viewId
                + JsfConstants.HIDDEN_SUBMIT_INPUT_SUFFIX;
        return formIdentifyKey;
    }

}
