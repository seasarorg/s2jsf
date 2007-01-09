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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author Satoshi Kimura
 */
public class ValidatorChain implements Validator, StateHolder {

    private boolean bTransient = false;

    List validators = new ArrayList();

    public void add(Validator validator) {
        validators.add(validator);
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        for (Iterator iterator = validators.iterator(); iterator.hasNext();) {
            Validator validator = (Validator)iterator.next();
            validator.validate(context, component, value);
        }
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[validators.size() + 1];
        for (int i = 0; i < validators.size(); i++) {
            Object validator = validators.get(i);
            if (validator instanceof StateHolder) {
                values[i] = ((StateHolder)validator).saveState(context);
            } else {
                values[i] = null;
            }
        }
        values[values.length - 1] = validators;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[])state;
        validators = (List)values[values.length - 1];
        for (int i = 0; i < validators.size(); i++) {
            Object validator = validators.get(i);
            if (validator instanceof StateHolder) {
                ((StateHolder)validator).restoreState(context, values[i]);
            }
        }
    }

    public boolean isTransient() {
        return bTransient;
    }

    public void setTransient(boolean transientValue) {
        this.bTransient = transientValue;
    }
}
