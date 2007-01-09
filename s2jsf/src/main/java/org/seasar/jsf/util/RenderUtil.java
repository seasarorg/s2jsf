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
package org.seasar.jsf.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.BooleanConversionUtil;
import org.seasar.framework.util.DoubleConversionUtil;
import org.seasar.framework.util.FloatConversionUtil;
import org.seasar.framework.util.IntegerConversionUtil;
import org.seasar.framework.util.LongConversionUtil;
import org.seasar.framework.util.ShortConversionUtil;
import org.seasar.jsf.JsfConstants;

/**
 * @author higa
 * @author yone
 */
public final class RenderUtil {

    private static Logger logger = Logger.getLogger(RenderUtil.class);

    private RenderUtil() {
    }

    public static void encodeChildren(FacesContext context,
            UIComponent component) throws IOException {

        Iterator i = component.getChildren().iterator();
        while (i.hasNext()) {
            encodeChild(context, (UIComponent) i.next());
            if (context.getResponseComplete()) {
                break;
            }
        }
    }

    public static void encodeChild(FacesContext context, UIComponent component)
            throws IOException {

        component.encodeBegin(context);
        if (component.getRendersChildren()) {
            component.encodeChildren(context);
        } else {
            encodeChildren(context, component);
        }
        component.encodeEnd(context);
    }

    public static boolean renderAttributes(ResponseWriter writer,
            UIComponent component, String[] attributeNames) throws IOException {

        boolean somethingDone = false;
        for (int i = 0, len = attributeNames.length; i < len; i++) {
            String attrName = attributeNames[i];
            if (renderAttribute(writer, component, attrName)) {
                somethingDone = true;
            }
        }
        return somethingDone;
    }

    public static boolean renderAttribute(ResponseWriter writer,
            UIComponent component, String attributeName) throws IOException {

        Object value = component.getAttributes().get(attributeName);
        return renderAttribute(writer, attributeName, value, attributeName);
    }

    public static boolean renderAttribute(ResponseWriter writer,
            String attributeName, Object value, String propertyName)
            throws IOException {
        if (value == null) {
            return false;
        }
        if (isDefaultAttributeValue(value)) {
            return false;
        }
        if (attributeName.equalsIgnoreCase(JsfConstants.STYLE_CLASS_ATTR)) {
            attributeName = JsfConstants.CLASS_ATTR;
        }
        if (attributeName.equalsIgnoreCase(JsfConstants.RENDERED_ATTR)) {
            return true;
        }
        writer.writeAttribute(attributeName, value, propertyName);
        return true;
    }

    public static void renderIdIfNecessary(ResponseWriter writer,
            UIComponent component, FacesContext context) throws IOException {

        String id = component.getId();
        if (id != null && !id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            writer.writeAttribute(JsfConstants.ID_ATTR, component
                    .getClientId(context), null);
        }
    }

    public static Object getConvertedUIOutputValue(FacesContext context,
            UIOutput output, Object submittedValue) throws ConverterException {

        if (submittedValue == null) {
            return null;
        }
        Converter converter = findConverterForSubmittedValue(context, output);
        if (converter == null) {
            return submittedValue;
        }
        return converter.getAsObject(context, output, (String) submittedValue);
    }

    public static Object getConvertedUIOutputValues(FacesContext context,
            UIOutput output, Object submittedValue) throws ConverterException {

        if (submittedValue == null) {
            return null;
        }
        Converter converter = findConverterForSubmittedValue(context, output);
        if (converter == null) {
            return submittedValue;
        }
        int length = Array.getLength(submittedValue);
        Class valueType = getValueType(context, output);
        Object ret = Array.newInstance(valueType, length);
        for (int i = 0; i < length; ++i) {
            Object o = converter.getAsObject(context, output, (String) Array
                    .get(submittedValue, i));
            setArrayValue(ret, valueType, o, i);
        }
        return ret;
    }

    protected static void setArrayValue(Object array, Class valueType,
            Object value, int index) {
        if (value == null) {
            return;
        }
        if (valueType == int.class) {
            Array.setInt(array, index, IntegerConversionUtil
                    .toPrimitiveInt(value));
        } else if (valueType == double.class) {
            Array.setDouble(array, index, DoubleConversionUtil
                    .toPrimitiveDouble(value));
        } else if (valueType == long.class) {
            Array.setLong(array, index, LongConversionUtil
                    .toPrimitiveLong(value));
        } else if (valueType == float.class) {
            Array.setFloat(array, index, FloatConversionUtil
                    .toPrimitiveFloat(value));
        } else if (valueType == short.class) {
            Array.setShort(array, index, ShortConversionUtil
                    .toPrimitiveShort(value));
        } else if (valueType == boolean.class) {
            Array.setBoolean(array, index, BooleanConversionUtil
                    .toPrimitiveBoolean(value));
        } else if (valueType == char.class) {
            Array.setChar(array, index, ((Character) value).charValue());
        }
        Array.set(array, index, value);
    }

    public static Converter findConverterForSubmittedValue(
            FacesContext context, UIOutput component) {

        Converter converter = component.getConverter();
        if (converter != null) {
            return converter;
        }
        Class valueType = getValueType(context, component);
        if (valueType == null) {
            return null;
        }
        if (String.class.equals(valueType) || Object.class.equals(valueType)) {
            return null;
        }
        try {
            return context.getApplication().createConverter(valueType);
        } catch (FacesException ex) {
            logger.log(ex);
            return null;
        }
    }

    public static Class getValueType(FacesContext context, UIOutput component) {
        ValueBinding vb = component.getValueBinding("value");
        if (vb == null) {
            return null;
        }
        Class valueType = vb.getType(context);
        if (valueType == null) {
            return null;
        }
        if (valueType.isArray()) {
            return valueType.getComponentType();
        } else {
            return valueType;
        }
    }

    static Renderer getRenderer(FacesContext context, UIComponent component) {
        String rendererType = component.getRendererType();
        if (rendererType == null) {
            return null;
        }
        RenderKitFactory renderKitFactory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        String renderKitId = context.getViewRoot().getRenderKitId();
        RenderKit renderKit = renderKitFactory.getRenderKit(context,
                renderKitId);
        return renderKit.getRenderer(component.getFamily(), rendererType);
    }

    public static Object getConvertedValue(FacesContext context,
            UIInput component, Object submittedValue) {
        try {
            Renderer renderer = getRenderer(context, component);
            if (renderer != null) {
                return renderer.getConvertedValue(context, component,
                        submittedValue);
            } else if (submittedValue instanceof String) {
                return getConvertedUIOutputValue(context, component,
                        submittedValue);
            }
        } catch (ConverterException e) {
            FacesMessage facesMessage = e.getFacesMessage();
            if (facesMessage != null) {
                context
                        .addMessage(component.getClientId(context),
                                facesMessage);
            } else {
                context.addMessage(component.getClientId(context), MessageUtil
                        .getErrorMessage(UIInput.CONVERSION_MESSAGE_ID,
                                new Object[] { UIComponentUtil
                                        .getLabel(component) }));
            }
            component.setValid(false);
        }
        return submittedValue;
    }

    public static boolean renderAttributesWithOptionalStartElement(
            ResponseWriter writer, UIComponent component, String elementName,
            String[] attributes) throws IOException {
        boolean startElementWritten = false;
        for (int i = 0, len = attributes.length; i < len; i++) {
            String attrName = attributes[i];
            Object value = component.getAttributes().get(attrName);
            if (!startElementWritten) {
                writer.startElement(elementName, component);
                startElementWritten = true;
            }
            renderAttribute(writer, attrName, value, attrName);
        }
        return startElementWritten;
    }

    public static boolean renderAttributeWithOptionalStartElement(
            ResponseWriter writer, UIComponent component, String elementName,
            String attrName, Object value, boolean startElementWritten)
            throws IOException {
        if (!startElementWritten) {
            writer.startElement(elementName, component);
            startElementWritten = true;
        }
        renderAttribute(writer, attrName, value, attrName);
        return startElementWritten;
    }

    public static boolean isDefaultAttributeValue(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue() == false;
        } else if (value instanceof Number) {
            if (value instanceof Integer) {
                return ((Number) value).intValue() == Integer.MIN_VALUE;
            } else if (value instanceof Double) {
                return ((Number) value).doubleValue() == Double.MIN_VALUE;
            } else if (value instanceof Long) {
                return ((Number) value).longValue() == Long.MIN_VALUE;
            } else if (value instanceof Byte) {
                return ((Number) value).byteValue() == Byte.MIN_VALUE;
            } else if (value instanceof Float) {
                return ((Number) value).floatValue() == Float.MIN_VALUE;
            } else if (value instanceof Short) {
                return ((Number) value).shortValue() == Short.MIN_VALUE;
            }
        }
        return false;
    }
    
}