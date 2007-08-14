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
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;

/**
 * @author higa
 */
public class ValidatorWrapper implements Validator, StateHolder {

    private boolean bTransient = false;

    private Validator validator;

    private UIParameter[] params;

    public ValidatorWrapper() {
    }

    public ValidatorWrapper(Validator validator) {
        setValidator(validator);
    }

    public ValidatorWrapper(Validator validator, UIParameter[] params) {
        setValidator(validator);
        setParams(params);
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        applyParams();
        validator.validate(context, component, value);
    }

    protected void applyParams() {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(validator.getClass());
        for (int i = 0; i < params.length; i++) {
            UIParameter param = params[i];
            String name = param.getName();
            Object value = param.getValue();
            if (beanDesc.hasPropertyDesc(name)) {
                PropertyDesc propertyDesc = beanDesc.getPropertyDesc(name);
                if (propertyDesc.hasWriteMethod()) {
                    propertyDesc.setValue(validator, value);
                }
            }
        }
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[3];
        values[0] = validator.getClass().getName();
        if (validator instanceof StateHolder) {
            values[1] = ((StateHolder) validator).saveState(context);
        }
        Object[] states = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            states[i] = params[i].saveState(context);
        }
        values[2] = states;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        validator = (Validator) ClassUtil.newInstance((String) values[0]);
        if (validator instanceof StateHolder) {
            ((StateHolder) validator).restoreState(context, values[1]);
        }
        Object[] states = (Object[]) values[2];
        params = new UIParameter[states.length];
        for (int i = 0; i < states.length; i++) {
            params[i] = new UIParameter();
            params[i].restoreState(context, states[i]);
        }
    }

    public boolean isTransient() {
        return bTransient;
    }

    public void setTransient(boolean transientValue) {
        this.bTransient = transientValue;
    }

    /**
     * @return Returns the params.
     */
    public UIParameter[] getParams() {
        return params;
    }

    /**
     * @param params
     *            The params to set.
     */
    public void setParams(UIParameter[] params) {
        this.params = params;
    }

    /**
     * @return Returns the validator.
     */
    public Validator getValidator() {
        return validator;
    }

    /**
     * @param validator
     *            The validator to set.
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
