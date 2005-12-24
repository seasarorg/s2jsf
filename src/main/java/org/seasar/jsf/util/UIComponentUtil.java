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
package org.seasar.jsf.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.jsf.JsfConstants;

/**
 * @author higa
 * 
 */
public final class UIComponentUtil {

    private UIComponentUtil() {
    }

    public static UIComponent findParent(UIComponent component,
            Class parentClass) {

        for (UIComponent parent = component.getParent(); parent != null; parent = parent
                .getParent()) {
            if (parentClass.isInstance(parent)) {
                return parent;
            }
        }
        throw new IllegalArgumentException(parentClass.getName());
    }

    public static String getLabel(UIComponent component) {
        String label = (String) component.getAttributes().get(
                JsfConstants.LABEL_ATTR);
        if (label != null) {
            return label;
        }
        return component.getId();
    }

    public static void callValidators(FacesContext context, UIInput input,
            Object convertedValue) {

        Validator[] validators = input.getValidators();
        for (int i = 0; i < validators.length; ++i) {
            Validator validator = validators[i];
            try {
                validator.validate(context, input, convertedValue);
            } catch (ValidatorException e) {
                input.setValid(false);
                FacesMessage facesMessage = e.getFacesMessage();
                if (facesMessage != null) {
                    context
                            .addMessage(input.getClientId(context),
                                    facesMessage);
                }
            }
        }
        MethodBinding validatorBinding = input.getValidator();
        if (validatorBinding != null) {
            try {
                validatorBinding.invoke(context, new Object[] { context, input,
                        convertedValue });
            } catch (EvaluationException e) {
                input.setValid(false);
                Throwable cause = e.getCause();
                if (cause instanceof ValidatorException) {
                    FacesMessage facesMessage = ((ValidatorException) cause)
                            .getFacesMessage();
                    if (facesMessage != null) {
                        context.addMessage(input.getClientId(context),
                                facesMessage);
                    }
                } else {
                    throw e;
                }
            }
        }
    }

    public static boolean isDisabled(UIComponent component) {
        return isTrue(component.getAttributes().get(JsfConstants.DISABLED_ATTR));
    }

    private static boolean isTrue(Object obj) {
        if (!(obj instanceof Boolean)) {
            return false;
        }
        return ((Boolean) obj).booleanValue();
    }

    public static boolean getBooleanAttribute(UIComponent component,
            String attrName) {
        return getBooleanAttribute(component, attrName, false);
    }

    public static boolean getBooleanAttribute(UIComponent component,
            String attrName, boolean defaultValue) {
        Boolean b = (Boolean) component.getAttributes().get(attrName);
        return b != null ? b.booleanValue() : defaultValue;
    }

}
