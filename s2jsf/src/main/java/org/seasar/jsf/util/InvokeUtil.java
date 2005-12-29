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

import java.lang.reflect.Field;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;

/**
 * @author higa
 * 
 */
public class InvokeUtil {

    private static final String EXPORT_SUFFIX = "_EXPORT";
    
    private static final String ACTION_BINDING_SUFFIX = "_ACTION_BINDING";
    
    private static final String VALUE = "value";
    
    private static final String BINDING_TYPE = "bindingType";
    
    private static final String NONE = "none";

    private static final String SESSION = "session";

    private InvokeUtil() {
    }

    public static String invoke(MethodBinding mb, FacesContext context) {
        String fromAction = mb.getExpressionString();
        String componentName = getComponentName(fromAction);
        S2Container container = SingletonS2ContainerFactory.getContainer();
        Object component = null;
        BeanDesc beanDesc = null;
        if (componentName != null && container.hasComponentDef(componentName)) {
            ComponentDef cd = container.getComponentDef(componentName);
            component = cd.getComponent();
            beanDesc = BeanDescFactory.getBeanDesc(cd.getConcreteClass());
        }
        if (component != null) {
            importVariables(component, container, beanDesc);
        }
        String outcome = invokeInternal(mb, context);
        if (component != null) {
            exportVariables(component, container, beanDesc);
        }
        return outcome;
    }

    private static String getComponentName(String fromAction) {
        if (fromAction == null) {
            return null;
        }
        int index = fromAction.indexOf('.');
        if (index > 0) {
            return fromAction.substring(2, index);
        }
        return null;
    }

    private static String invokeInternal(MethodBinding mb, FacesContext context) {
        try {
            return (String) mb.invoke(context, null);
        } catch (EvaluationException ex) {
            Throwable cause = ex.getCause();
            if (cause != null && cause instanceof AbortProcessingException) {
                throw (AbortProcessingException) cause;
            }
            throw ex;
        }
    }

    private static void importVariables(Object component,
            S2Container container, BeanDesc beanDesc) {

        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.hasWriteMethod()) {
                String varName = pd.getPropertyName();
                String fieldName = pd.getPropertyName() + ACTION_BINDING_SUFFIX;
                if (beanDesc.hasField(fieldName)) {
                    String bindingStr = (String) beanDesc.getFieldValue(fieldName, null);
                    if (bindingStr != null) {
                        String[] array = StringUtil.split(bindingStr, "=, ");
                        if (array.length == 1) {
                            varName = array[0];
                        } else {
                            boolean nobinding = false;
                            for (int j = 0; j < array.length; j += 2) {
                                String k = array[j].trim();
                                String v = array[j + 1].trim();
                                if (BINDING_TYPE.equalsIgnoreCase(k)) {
                                    nobinding = NONE.equalsIgnoreCase(v);
                                } else if (VALUE.equalsIgnoreCase(k)) {
                                    varName = v;
                                } else {
                                    throw new IllegalArgumentException(bindingStr);
                                }
                            }
                            if (nobinding) {
                                continue;
                            }
                        }
                    }
                }
                if (container.hasComponentDef(varName)) {
                    continue;
                }
                Object var = BindingUtil.getValue(container.getRequest(), varName);
                if (var != null) {
                    pd.setValue(component, var);
                }
            }
        }
    }

    private static void exportVariables(Object component,
            S2Container container, BeanDesc beanDesc) {

        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod()) {
                Object var = pd.getValue(component);
                if (var != null) {
                    boolean useSession = false;
                    String fieldName = pd.getPropertyName() + EXPORT_SUFFIX;
                    if (beanDesc.hasField(fieldName)) {
                        Field field = beanDesc.getField(fieldName);
                        String value = (String) FieldUtil.get(field, null);
                        if (SESSION.equalsIgnoreCase(value)) {
                            useSession = true;
                        }
                    } else if (container.hasComponentDef(pd.getPropertyName())) {
                        ComponentDef cd = container.getComponentDef(pd
                                .getPropertyName());
                        useSession = InstanceDefFactory.SESSION.equals(cd
                                .getInstanceDef());
                    }
                    if (useSession) {
                        container.getSession().setAttribute(
                                pd.getPropertyName(), var);
                    } else {
                        container.getRequest().setAttribute(
                                pd.getPropertyName(), var);
                    }
                }
            }
        }
    }
}