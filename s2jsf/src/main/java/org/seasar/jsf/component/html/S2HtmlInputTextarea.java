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
package org.seasar.jsf.component.html;

import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeEvent;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.MessageUtil;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.UIComponentUtil;
import org.seasar.jsf.util.UIValueUtil;

/**
 * @author higa
 *  
 */
public class S2HtmlInputTextarea extends HtmlInputTextarea {

    private String label;

    public String getLabel() {
        if (label != null) {
            return label;
        }
        ValueBinding vb = getValueBinding(JsfConstants.LABEL_ATTR);
        return vb != null ? (String) vb.getValue(getFacesContext()) : getId();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(Object value) {
        Object newValue = value;
        if ("".equals(value)) {
            newValue = null;
        }
        super.setValue(newValue);
    }

    public void validate(FacesContext context) {
        Object submittedValue = getSubmittedValue();
        Object convertedValue = RenderUtil.getConvertedValue(context, this,
                submittedValue);
        if (!isValid()) {
            return;
        }
        boolean empty = UIValueUtil.isEmpty(convertedValue);
        if (isRequired() && empty) {
            context.addMessage(getClientId(context), MessageUtil
                    .getErrorMessage(REQUIRED_MESSAGE_ID,
                            new Object[] { getLabel() }));
            setValid(false);
            return;
        }
        if (!empty) {
            UIComponentUtil.callValidators(context, this, convertedValue);
        }
        if (!isValid()) {
            return;
        }
        if (isReadonly() || isDisabled()) {
            return;
        }
        Object previousValue = getValue();
        setValue(convertedValue);
        setSubmittedValue(null);
        if (compareValues(previousValue, convertedValue)) {
            queueEvent(new ValueChangeEvent(this, previousValue, convertedValue));
        }
    }
    
    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = label;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        label = (String) values[1];
    }
}