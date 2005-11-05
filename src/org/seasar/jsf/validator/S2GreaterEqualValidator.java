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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.seasar.jsf.util.MessageUtil;
import org.seasar.jsf.util.UIComponentUtil;

/**
 * @author higa
 *
 */
public class S2GreaterEqualValidator extends S2CompareValidator {

    public static final String GE_MESSAGE_ID = "org.seasar.jsf.validator.S2GreaterEqualValidator.GE";
    
    /**
     * @see org.seasar.jsf.validator.S2CompareValidator#doValidate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object, javax.faces.component.UIComponent, java.lang.Object)
     */
    protected void doValidate(FacesContext context, UIComponent component,
            Object value, UIComponent targetComponent, Object targetValue)
            throws ValidatorException {

        if (targetValue == null) {
            return;
        }
        if (!(value instanceof Comparable) ||
                ((Comparable) value).compareTo(targetValue) < 0) {
            
            Object[] args = { UIComponentUtil.getLabel(component), UIComponentUtil.getLabel(targetComponent) };
            throw new ValidatorException(MessageUtil.getS2ErrorMessage(
                    GE_MESSAGE_ID, args));
        }
    }
}
