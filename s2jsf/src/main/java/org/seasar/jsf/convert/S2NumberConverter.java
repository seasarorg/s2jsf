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
package org.seasar.jsf.convert;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.NumberConverter;

import org.seasar.jsf.util.MessageUtil;
import org.seasar.jsf.util.UIComponentUtil;

/**
 * @author manhole
 */
public class S2NumberConverter extends NumberConverter {

    protected static final String CONVERSION_MESSAGE_ID = "javax.faces.convert.NumberConverter.CONVERSION";

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
        NumberFormat format = getNumberFormat(context);
        format.setParseIntegerOnly(isIntegerOnly());
        try {
            return format.parse(value);
        } catch (ParseException e) {
            throw new ConverterException(MessageUtil.getErrorMessage(
                    CONVERSION_MESSAGE_ID, new Object[] {
                            UIComponentUtil.getLabel(component), value }), e);
        }
    }

    protected NumberFormat getNumberFormat(FacesContext context) {
        Locale locale = getLocale();
        if (locale == null) {
            locale = context.getViewRoot().getLocale();
        }
        final String pattern = getPattern();
        final String type = getType();
        if (pattern == null && type == null) {
            throw new ConverterException(
                    "Cannot get NumberFormat, either type or pattern needed.");
        }
        if (pattern != null) {
            return new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
        }

        if (type.equals("number")) {
            return NumberFormat.getNumberInstance(locale);
        } else if (type.equals("currency")) {
            return NumberFormat.getCurrencyInstance(locale);
        } else if (type.equals("percentage")) {
            return NumberFormat.getPercentInstance(locale);
        }
        throw new ConverterException("Cannot get NumberFormat, illegal type "
                + type);
    }

}
