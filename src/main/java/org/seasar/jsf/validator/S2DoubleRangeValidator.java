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
package org.seasar.jsf.validator;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.jsf.util.DoubleValidatorUtil;
import org.seasar.jsf.util.MessageUtil;
import org.seasar.jsf.util.UIComponentUtil;

/**
 * @author higa
 *
 */
public class S2DoubleRangeValidator implements Validator, StateHolder {

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MINIMUM";

    public static final String TYPE_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.TYPE";

    private Double minimum = null;

    private Double maximum = null;

    private boolean bTransient = false;

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        if (value == null) {
            return;
        }
        double dvalue = DoubleValidatorUtil.parseDoubleValue(context, component, value);
        if (minimum != null && maximum != null) {
            if (dvalue < minimum.doubleValue()
                    || dvalue > maximum.doubleValue()) {
                Object[] args = { minimum, maximum,
                        UIComponentUtil.getLabel(component) };
                throw new ValidatorException(MessageUtil.getErrorMessage(
                        NOT_IN_RANGE_MESSAGE_ID, args));
            }
        } else if (minimum != null) {
            if (dvalue < minimum.doubleValue()) {
                Object[] args = { minimum, UIComponentUtil.getLabel(component) };
                throw new ValidatorException(MessageUtil.getErrorMessage(
                        MINIMUM_MESSAGE_ID, args));
            }
        } else if (maximum != null) {
            if (dvalue > maximum.doubleValue()) {
                Object[] args = { maximum, UIComponentUtil.getLabel(component) };
                throw new ValidatorException(MessageUtil.getErrorMessage(
                        MAXIMUM_MESSAGE_ID, args));
            }
        }
    }

    public double getMaximum() {
        return maximum != null ? maximum.doubleValue() : Double.MAX_VALUE;
    }

    public void setMaximum(double maximum) {
        this.maximum = new Double(maximum);
    }

    public double getMinimum() {
        return minimum != null ? minimum.doubleValue() : Double.MIN_VALUE;
    }

    public void setMinimum(double minimum) {
        this.minimum = new Double(minimum);
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
        maximum = (Double) values[0];
        minimum = (Double) values[1];
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof S2DoubleRangeValidator)) {
            return false;
        }
        final S2DoubleRangeValidator doubleRangeValidator = (S2DoubleRangeValidator) o;
        if (maximum != null ? !maximum.equals(doubleRangeValidator.maximum)
                : doubleRangeValidator.maximum != null) {
            return false;
        }
        if (minimum != null ? !minimum.equals(doubleRangeValidator.minimum)
                : doubleRangeValidator.minimum != null) {
            return false;
        }
        return true;
    }
}