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
package org.seasar.jsf.validator;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.ValueHolderUtil;

/**
 * @author cero-t
 * 
 */
public abstract class S2AnywhereValidator implements Validator, StateHolder {

    private String targetId = null;

    private boolean bTransient = false;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public boolean isTransient() {
        return bTransient;
    }

    public void setTransient(boolean transientValue) {
        this.bTransient = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[1];
        values[0] = targetId;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        targetId = (String) values[0];
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        if (value == null) {
            return;
        }
        UIComponent[] targetComponents = getTargetComponents(component);
        Object[] targetValues = new Object[targetComponents.length];

        for (int i = 0; i < targetComponents.length; i++) {
            if (targetComponents[i] instanceof UIInput) {
                UIInput input = (UIInput) targetComponents[i];

                if (input.isLocalValueSet()) {
                    targetValues[i] = input.getLocalValue();
                } else {
                    targetValues[i] = RenderUtil.getConvertedValue(context,
                            input, input.getSubmittedValue());
                }
            } else {
                targetValues[i] = ValueHolderUtil.getValue(targetComponents[i]);
            }
        }
        doValidate(context, component, value, targetComponents, targetValues);
    }

    protected UIComponent[] getTargetComponents(UIComponent component) {
        if (targetId == null) {
            throw new EmptyRuntimeException("targetId");
        }
        String[] targetIds = targetId.split(",");
        UIComponent[] targetComponents = new UIComponent[targetIds.length];

        for (int i = 0; i < targetIds.length; i++) {
            targetComponents[i] = component.findComponent(targetIds[i]);
            if (targetComponents[i] == null) {
                throw new EmptyRuntimeException(targetIds[i]);
            }
        }

        return targetComponents;
    }

    protected abstract void doValidate(FacesContext context,
            UIComponent component, Object value, UIComponent[] targetComponent,
            Object targetValues[]) throws ValidatorException;
}