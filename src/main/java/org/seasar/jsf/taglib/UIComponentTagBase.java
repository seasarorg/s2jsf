/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.jsf.taglib;

import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.webapp.UIComponentTag;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.el.SimpleMethodBinding;
import org.seasar.jsf.exception.NoEditableValueHolderRuntimeException;
import org.seasar.jsf.exception.NoUICommandRuntimeException;
import org.seasar.jsf.exception.NoValueHolderRuntimeException;
import org.seasar.jsf.exception.NoValueReferenceRuntimeException;
import org.seasar.jsf.util.BindingUtil;

/**
 * @author higa
 *  
 */
public abstract class UIComponentTagBase extends UIComponentTag {

	private static final Class[] VALIDATOR_ARGTYPES = { FacesContext.class,
			UIComponent.class, Object.class };

	private String transient_;

	private String value;

	private String converter;

	public UIComponentTagBase() {
	}

	public void setTransient(String t) {
		this.transient_ = t;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setConverter(String converter) {
		this.converter = converter;
	}
	
	public void release() {
		super.release();
		transient_ = null;
		value = null;
		converter = null;
	}
	
	protected void setProperties(UIComponent component){
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.TRANSIENT_ATTR, transient_);
        setComponentProperty(component, JsfConstants.VALUE_ATTR, value);
        setConverterProperty(component, converter);
    }

	protected void setComponentProperty(UIComponent component,
			String propertyName, String value) {
		if (value == null) {
			return;
		}
		if (BindingUtil.isValueReference(value)) {
			BindingUtil.setValueBinding(component, propertyName, value);
		} else {
			setBeanProperty(component, propertyName, value);
		}
	}
	
	protected void setBeanProperty(UIComponent component,
			String propertyName, String value) {
		
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component
				.getClass());
		if (beanDesc.hasPropertyDesc(propertyName)) {
			PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
			if (pd.hasWriteMethod()) {
				pd.setValue(component, value);
			}
		} else {
			component.getAttributes().put(propertyName, value);
		}
	}

	protected void setConverterProperty(UIComponent component, String value) {
		if (value == null) {
			return;
		}
		if (!(component instanceof ValueHolder)) {
			throw new NoValueHolderRuntimeException(component.getClass());
		}
		if (isValueReference(value)) {
			BindingUtil.setValueBinding(component, JsfConstants.CONVERTER_ATTR,
					value);
		} else {
			Converter converter = createConverter(value);
			((ValueHolder) component).setConverter(converter);
		}
	}

	protected void setValidatorProperty(UIComponent component, String value) {
		if (value == null) {
			return;
		}
		if (!(component instanceof EditableValueHolder)) {
			throw new NoEditableValueHolderRuntimeException(component
					.getClass());
		}
		if (!isValueReference(value)) {
			throw new NoValueReferenceRuntimeException(value);
		}
		MethodBinding mb = createMethodBinding(value, VALIDATOR_ARGTYPES);
		((EditableValueHolder) component).setValidator(mb);
	}

	protected void setActionProperty(UIComponent component, String value) {
		if (value == null) {
			return;
		}
		if (!(component instanceof UICommand)) {
			throw new NoUICommandRuntimeException(component.getClass());
		}
		MethodBinding binding = null;
		if (isValueReference(value)) {
			binding = createMethodBinding(value, null);
		} else {
			binding = new SimpleMethodBinding(value);
		}
		((UICommand) component).setAction(binding);
	}

	protected Application getApplication() {
		return getFacesContext().getApplication();
	}

	protected Converter createConverter(String value) {
		return getApplication().createConverter(value);
	}

	protected MethodBinding createMethodBinding(String value, Class[] argTypes) {
		return getApplication().createMethodBinding(value, argTypes);
	}
}