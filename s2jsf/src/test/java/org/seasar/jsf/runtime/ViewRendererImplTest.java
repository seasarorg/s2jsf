/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.jsf.runtime;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.validator.Validator;

import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.jsf.mock.MockFacesContext;

/**
 * @author yone
 */
public class ViewRendererImplTest extends S2FrameworkTestCase {

    public void testExecuteInitActionOutcomeIsNull() throws Exception {
        ViewRendererImpl v = new ViewRendererImpl(null, null, null);
        MockFacesContextOrg context = new MockFacesContextOrg();
        MockApplicationImpl application = new MockApplicationImpl();
        context.setApplication(application);
        application.setMethodBinding(new MockMethodBinding2());

        assertFalse(v.executeInitAction(context, null));
    }

    public void testExecuteInitActionResponseComplete() throws Exception {
        ViewRendererImpl v = new ViewRendererImpl(null, null, null);
        MockFacesContextOrg context = new MockFacesContextOrg();
        MockApplicationImpl application = new MockApplicationImpl();
        context.setApplication(application);
        application.setMethodBinding(new MockMethodBinding1());

        assertTrue(v.executeInitAction(context, null));
    }

    public static class MockFacesContextOrg extends MockFacesContext {
        private Application app;

        public void setApplication(Application app) {
            this.app = app;
        }

        public Application getApplication() {
            return this.app;
        }
    }

    public static class MockMethodBinding1 extends MethodBinding {
        public Object invoke(FacesContext context, Object[] params) {
            MockFacesContext.getCurrentInstance().responseComplete();
            return "hoge";
        }

        public Class getType(FacesContext arg0) throws MethodNotFoundException {
            return null;
        }
    }

    public static class MockMethodBinding2 extends MockMethodBinding1 {
        public Object invoke(FacesContext context, Object[] params) {
            return null;
        }
    }

    public static class MockApplicationImpl extends Application {
        private MethodBinding mb;

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

        public UIComponent createComponent(ValueBinding arg0,
                FacesContext arg1, String arg2) throws FacesException {
            return null;
        }

        public Iterator getComponentTypes() {
            return null;
        }

        public void addConverter(String arg0, String arg1) {
        }

        public void addConverter(Class arg0, String arg1) {
        }

        public Converter createConverter(String arg0) {
            return null;
        }

        public Converter createConverter(Class arg0) {
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

        public void addValidator(String arg0, String arg1) {
        }

        public Validator createValidator(String arg0) throws FacesException {
            return null;
        }

        public Iterator getValidatorIds() {
            return null;
        }

        public ValueBinding createValueBinding(String arg0)
                throws ReferenceSyntaxException {
            return null;
        }

    }
}