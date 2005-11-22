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
package org.seasar.jsf.render.html;

import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

import org.seasar.jsf.exception.NoEditableValueHolderRuntimeException;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.UIComponentUtil;

/**
 * @author higa
 *  
 */
public class HtmlSelectOneRadioRenderer extends Renderer {

    public void decode(FacesContext context, UIComponent component) {
        if (!(component instanceof EditableValueHolder)) {
            throw new NoEditableValueHolderRuntimeException(component
                .getClass());
        }
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String clientId = component.getClientId(context);
        if (paramMap.containsKey(clientId)) {
            String submittedValue = (String) paramMap.get(clientId);
            if ("on".equalsIgnoreCase(submittedValue)) {
                submittedValue = "";
            }
            ((EditableValueHolder) component).setSubmittedValue(submittedValue);
        } else {
            if (!UIComponentUtil.isDisabledOrReadOnly(component)) {
                ((EditableValueHolder) component).setSubmittedValue("");
            }
        }
    }

    public Object getConvertedValue(FacesContext context,
        UIComponent component, Object submittedValue) throws ConverterException {

        return RenderUtil.getConvertedUIOutputValue(context,
            (UIOutput) component, submittedValue);
    }
}