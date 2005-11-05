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

import java.lang.reflect.Array;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.seasar.jsf.exception.NoValueHolderRuntimeException;

/**
 * @author higa
 *  
 */
public final class ValueHolderUtil {

	private ValueHolderUtil() {
	}
    
    public static Object getValue(UIComponent component) {
        if (!(component instanceof ValueHolder)) {
            throw new NoValueHolderRuntimeException(component.getClass());
        }
        ValueHolder vh = (ValueHolder) component;
        return vh.getValue();
    }

	public static String getValueAsString(FacesContext context,
			UIComponent component) {

		if (!(component instanceof ValueHolder)) {
			throw new NoValueHolderRuntimeException(component.getClass());
		}
		if (component instanceof EditableValueHolder) {
			EditableValueHolder evh = (EditableValueHolder) component;
			Object submittedValue = evh.getSubmittedValue();
			if (submittedValue != null) {
				if (submittedValue instanceof String) {
					return (String) submittedValue;
				}
				return submittedValue.toString();
			}
		}
		ValueHolder vh = (ValueHolder) component;
		Object value = vh.getValue();
		Converter converter = vh.getConverter();
		return UIValueUtil.getValueAsString(context, component, value, converter);
	}
    
    public static String[] getValueAsStringArray(FacesContext context,
            UIComponent component) {

        if (!(component instanceof ValueHolder)) {
            throw new NoValueHolderRuntimeException(component.getClass());
        }
        if (component instanceof EditableValueHolder) {
            EditableValueHolder evh = (EditableValueHolder) component;
            Object submittedValue = evh.getSubmittedValue();
            if (submittedValue instanceof String[]) {
                return (String[]) submittedValue;
            }
        }
        ValueHolder vh = (ValueHolder) component;
        Object value = vh.getValue();
        if (value == null) {
            return new String[0];
        }
        Converter converter = vh.getConverter();
        int length = Array.getLength(value); 
        String[] ret = new String[length];
        for (int i = 0; i < length; ++i) {
            ret[i] = UIValueUtil.getValueAsString(context, component, Array.get(value, i), converter); 
        }
        return ret;
    }
}