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
package org.seasar.jsf.validator;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.jsf.util.MessageUtil;
import org.seasar.jsf.util.UIComponentUtil;

/**
 * @author higa
 *
 */
public class S2LengthValidator implements Validator, StateHolder {

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MINIMUM";

    private Integer minimum = null;

    private Integer maximum = null;

    private boolean bTransient = false;

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        if (value == null) {
            return;
        }
        String s = value instanceof String ? (String) value : value.toString();
        int length = s.length();
        if (minimum != null) {
            if (length < minimum.intValue()) {
                Object[] args = { minimum, UIComponentUtil.getLabel(component)};
                throw new ValidatorException(MessageUtil.getErrorMessage(
                        MINIMUM_MESSAGE_ID, args));
            }
        }
        if (maximum != null) {
            if (length > maximum.intValue()) {
                Object[] args = { maximum, UIComponentUtil.getLabel(component) };
                throw new ValidatorException(MessageUtil.getErrorMessage(
                        MAXIMUM_MESSAGE_ID, args));
            }
        }
    }

    public int getMaximum() {
        return maximum != null ? maximum.intValue() : Integer.MAX_VALUE;
    }

    public void setMaximum(int maximum) {
        this.maximum = new Integer(maximum);
    }

    public int getMinimum() {
        return minimum != null ? minimum.intValue() : 0;
    }

    public void setMinimum(int minimum) {
        this.minimum = new Integer(minimum);
    }

    public boolean isTransient() {
        return bTransient;
    }

    public void setTransient(boolean transientValue) {
        this.bTransient = transientValue;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = maximum;
        values[1] = minimum;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        maximum = (Integer) values[0];
        minimum = (Integer) values[1];
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof S2LengthValidator)) {
            return false;
        }
        final S2LengthValidator lengthValidator = (S2LengthValidator) o;
        if (maximum != null ? !maximum.equals(lengthValidator.maximum)
                : lengthValidator.maximum != null) {
            return false;
        }
        if (minimum != null ? !minimum.equals(lengthValidator.minimum)
                : lengthValidator.minimum != null) {
            return false;
        }
        return true;
    }
}