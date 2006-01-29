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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.faces.webapp.ValidatorTag;
import javax.servlet.jsp.JspException;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.BindingUtil;

/**
 * @author higa
 *  
 */
public class S2ValidatorTag extends ValidatorTag {

	private static final long serialVersionUID = 1L;

    private String binding;
    
    private Map attributes = new HashMap();
	
	public S2ValidatorTag() {
	}
	
	public String getBinding() {
		return binding;
	}
	
	public void setBinding(String binding) {
		if (binding != null && !UIComponentTag.isValueReference(binding)) {
			throw new IllegalArgumentException(JsfConstants.BINDING_ATTR);
		}
		this.binding = binding;
	}
    
    public void addAttribute(String name, String value) {
        if (JsfConstants.BINDING_ATTR.equalsIgnoreCase(name)) {
            setBinding(value);
        } else {
            attributes.put(name, value);
        }
    }
	
	public void release(){
        super.release();
        binding = null;
        attributes.clear();
    }

	protected Validator createValidator() throws JspException {
		if (binding == null) {
            throw new EmptyRuntimeException(JsfConstants.BINDING_ATTR);
		}
        Validator validator = (Validator) BindingUtil.resolveBinding(binding);
        setupAttributes(validator);
        return validator;
	}
    
    protected void setupAttributes(Validator validator) {
        if (attributes.isEmpty()) {
            return;
        }
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(validator.getClass());
        for (Iterator i = attributes.keySet().iterator(); i.hasNext(); ) {
            String name = (String) i.next();
            if (!beanDesc.hasPropertyDesc(name)) {
                continue;
            }
            Object value = attributes.get(name);
            String s = (String) value;
            if (UIComponentTag.isValueReference(s)) {
                value = BindingUtil.resolveBinding(s);
            }
            PropertyDesc pd = beanDesc.getPropertyDesc(name);
            pd.setValue(validator, value);
        }
    }
}