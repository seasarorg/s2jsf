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
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.jsf.util.LongValidatorUtil;
import org.seasar.jsf.util.MessageUtil;
import org.seasar.jsf.util.UIComponentUtil;

/**
 * @author higa
 *
 */
public class S2LongRangeValidator implements Validator, StateHolder {

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.LongRangeValidator.MINIMUM";

    private Long minimum = null;

    private Long maximum = null;

    private boolean bTransient = false;

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        if (value == null) {
            return;
        }
        double dvalue = LongValidatorUtil.parseLongValue(context, component, value);
        if (minimum != null && maximum != null) {
            if (dvalue < minimum.longValue() || dvalue > maximum.longValue()) {
                Object[] args = { minimum, maximum,
                        UIComponentUtil.getLabel(component) };
                throw new ValidatorException(MessageUtil.getErrorMessage(
                        NOT_IN_RANGE_MESSAGE_ID, args));
            }
        } else if (minimum != null) {
            if (dvalue < minimum.longValue()) {
                Object[] args = { minimum, UIComponentUtil.getLabel(component) };
                throw new ValidatorException(MessageUtil.getErrorMessage(
                        MINIMUM_MESSAGE_ID, args));
            }
        } else if (maximum != null) {
            if (dvalue > maximum.longValue()) {
                Object[] args = { maximum, UIComponentUtil.getLabel(component) };
                throw new ValidatorException(MessageUtil.getErrorMessage(
                        MAXIMUM_MESSAGE_ID, args));
            }
        }
    }

    public long getMaximum() {
        return maximum != null ? maximum.longValue() : Long.MAX_VALUE;
    }

    public void setMaximum(long maximum) {
        this.maximum = new Long(maximum);
    }

    public long getMinimum() {
        return minimum != null ? minimum.longValue() : Long.MIN_VALUE;
    }

    public void setMinimum(long minimum) {
        this.minimum = new Long(minimum);
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
        maximum = (Long) values[0];
        minimum = (Long) values[1];
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof S2LongRangeValidator)) {
            return false;
        }
        final S2LongRangeValidator longRangeValidator = (S2LongRangeValidator) o;
        if (maximum != null ? !maximum.equals(longRangeValidator.maximum)
                : longRangeValidator.maximum != null) {
            return false;
        }
        if (minimum != null ? !minimum.equals(longRangeValidator.minimum)
                : longRangeValidator.minimum != null) {
            return false;
        }
        return true;
    }
}