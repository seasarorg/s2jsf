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
package org.seasar.jsf.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ByteConverter;
import javax.faces.convert.ConverterException;

import org.seasar.jsf.util.MessageUtil;
import org.seasar.jsf.util.UIComponentUtil;

/**
 * @author manhole
 */
public class S2ByteConverter extends ByteConverter {

    protected static final String CONVERSION_MESSAGE_ID = "javax.faces.convert.ByteConverter.CONVERSION";

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (context == null) {
            throw new NullPointerException("context");
        }
        if (component == null) {
            throw new NullPointerException("component");
        }
        if (value == null) {
            return null;
        }
        value = value.trim();
        if (value.length() == 0) {
            return null;
        }
        try {
            return Byte.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ConverterException(MessageUtil.getErrorMessage(
                    CONVERSION_MESSAGE_ID, new Object[] { value,
                            UIComponentUtil.getLabel(component) }), e);
        }
    }

}