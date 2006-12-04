package org.seasar.jsf.mock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.validator.Validator;

import org.seasar.framework.util.ClassUtil;

public class MockApplication extends Application {
    private ValueBinding vb;

    private MethodBinding mb;

    private Map convertersById_ = new HashMap();

    private Map convertersByClass_ = new HashMap();

    private Map validators_ = new HashMap();
    
    public void setValueBinding(ValueBinding vb) {
        this.vb = vb;
    }

    public ValueBinding createValueBinding(String arg0)
            throws ReferenceSyntaxException {
        return this.vb;
    }

    public void setMethodBinding(MethodBinding mb) {
        this.mb = mb;
    }

    public MethodBinding createMethodBinding(String ref, Class[] params)
            throws ReferenceSyntaxException {
        return this.mb;
    }

    public ActionListener getActionListener() {
        return null;
    }

    public void setActionListener(ActionListener arg0) {
    }

    public Locale getDefaultLocale() {
        return null;
    }

    public void setDefaultLocale(Locale arg0) {
    }

    public String getDefaultRenderKitId() {
        return null;
    }

    public void setDefaultRenderKitId(String arg0) {
    }

    public String getMessageBundle() {
        return null;
    }

    public void setMessageBundle(String arg0) {
    }

    public NavigationHandler getNavigationHandler() {
        return null;
    }

    public void setNavigationHandler(NavigationHandler arg0) {
    }

    public PropertyResolver getPropertyResolver() {
        return null;
    }

    public void setPropertyResolver(PropertyResolver arg0) {
    }

    public VariableResolver getVariableResolver() {
        return null;
    }

    public void setVariableResolver(VariableResolver arg0) {
    }

    public ViewHandler getViewHandler() {
        return null;
    }

    public void setViewHandler(ViewHandler arg0) {
    }

    public StateManager getStateManager() {
        return null;
    }

    public void setStateManager(StateManager arg0) {
    }

    public void addComponent(String arg0, String arg1) {
    }

    public UIComponent createComponent(String arg0) throws FacesException {
        return null;
    }

    public UIComponent createComponent(ValueBinding arg0, FacesContext arg1,
            String arg2) throws FacesException {
        return null;
    }

    public Iterator getComponentTypes() {
        return null;
    }

    public Iterator getConverterIds() {
        return null;
    }

    public Iterator getConverterTypes() {
        return null;
    }

    public Iterator getSupportedLocales() {
        return null;
    }

    public void setSupportedLocales(Collection arg0) {
    }

    public Iterator getValidatorIds() {
        return null;
    }
    
    public void addConverter(String converterId, String converterClass) {
        if (converterId == null || converterClass == null) {
            throw new IllegalArgumentException();
        }
        convertersById_.put(converterId, converterClass);
    }

    public void addConverter(Class targetClass, String converterClass) {
        if (targetClass == null || converterClass == null) {
            throw new IllegalArgumentException();
        }
        convertersByClass_.put(targetClass, converterClass);
    }

    public Converter createConverter(String converterId) {
        Converter converter = null;
        if (convertersById_.containsKey(converterId)) {
            String className = (String) convertersById_.get(converterId);
            converter = (Converter) ClassUtil.newInstance(className);
        }
        return converter;
    }

    public Converter createConverter(Class targetClass) {
        Converter converter = null;
        if (convertersByClass_.containsKey(targetClass)) {
            String className = (String) convertersByClass_.get(targetClass);
            converter = (Converter) ClassUtil.newInstance(className);
        }
        return converter;
    }

    public void addValidator(String validatorId, String validatorClass) {
        validators_.put(validatorId, validatorClass);
    }

    public Validator createValidator(String validatorId) throws FacesException {
        String validatorClass = (String) validators_.get(validatorId);
        Validator v = null;
        if (validatorClass != null) {
            v = (Validator) ClassUtil.newInstance(validatorClass);
        }
        return v;
    }
    
}