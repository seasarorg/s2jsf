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
package org.seasar.jsf.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.jsf.ErrorPageManager;
import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.JsfContext;
import org.seasar.jsf.TagProcessor;
import org.seasar.jsf.ViewTemplate;
import org.seasar.jsf.ViewTemplateFactory;
import org.seasar.jsf.exception.TagProcessorNotFoundRuntimeException;
import org.seasar.jsf.util.BindingUtil;
import org.seasar.jsf.util.ExternalContextUtil;
import org.seasar.jsf.util.InvokeUtil;
import org.xml.sax.Attributes;

/**
 * @author higa
 * 
 */
public class InsertProcessor extends TagProcessorImpl {

    public static final String DYNAMIC_PAGE_ATTR = InsertProcessor.class
            .getName()
            + ".DYNAMIC_PAGE";

    public InsertProcessor(String inject) {
        super(inject);
    }

    public void addChild(TagProcessor child) {
        if (getProperty(JsfConstants.SRC_ATTR) != null) {
            return;
        }
        super.addChild(child);
    }

    public void setup(String namespaceURI, String localName, String qName,
            Attributes attributes, JsfConfig jsfConfig) {

        super.setup(namespaceURI, localName, qName, attributes, jsfConfig);
        ViewProcessor viewProcessor = (ViewProcessor) findAncestor(ViewProcessor.class);
        if (viewProcessor == null) {
            throw new TagProcessorNotFoundRuntimeException(ViewProcessor.class);
        }
        viewProcessor.addInsertProcessor(this);
    }

    public void process(JsfContext jsfContext, Tag parentTag)
            throws JspException {

        Map insertProcessorMap = getInsertProcessorMap(jsfContext
                .getPageContext());
        String name = getName();
        if (name != null && insertProcessorMap.containsKey(name)) {
            InsertProcessor ip = (InsertProcessor) insertProcessorMap.get(name);
            if (ip != this) {
                ip.process(jsfContext, parentTag);
                return;
            }
        }

        String src = getSrc();
        if (BindingUtil.isValueReference(src)) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            String viewId = ExternalContextUtil.getViewId(externalContext);
            externalContext.getSessionMap().put(
                    DYNAMIC_PAGE_ATTR + "-" + viewId, Boolean.TRUE);
        }

        String[] srcs = getSrcs();
        if (srcs != null) {
            for (int i = 0; i < srcs.length; i++) {
                processInclude(jsfContext, parentTag, srcs[i]);
            }
        } else {
            processChildren(jsfContext, parentTag);
        }
    }

    public String[] getSrcs() {
        String src = getSrc();

        if (src == null) {
            return null;
        } else if (!BindingUtil.isValueReference(src)) {
            return new String[] { src };
        }

        Object value = BindingUtil.resolveBinding(src);
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return new String[] { (String) value };
        } else if (value instanceof Collection) {
            return (String[]) new ArrayList((Collection) value)
                    .toArray(new String[0]);
        } else if (value.getClass().isArray()) {
            return (String[]) value;
        } else {
            throw new IllegalStateException(JsfConstants.SRC_ATTR);
        }
    }

    public String getName() {
        return getProperty(JsfConstants.NAME_ATTR);
    }

    public String getSrc() {
        return getProperty(JsfConstants.SRC_ATTR);
    }

    protected void processInclude(JsfContext jsfContext, Tag parentTag,
            String src) throws JspException {

        S2Container container = SingletonS2ContainerFactory.getContainer();
        ViewTemplateFactory factory = (ViewTemplateFactory) container
                .getComponent(ViewTemplateFactory.class);
        ViewTemplate template = factory.getViewTemplate(src);
        ViewProcessor viewProcessor = (ViewProcessor) template
                .getRootTagProcessor();
        InsertProcessor insertProcessor = viewProcessor
                .getInsertProcessor(null);

        String initAction = viewProcessor.getInitAction();
        FacesContext context = FacesContext.getCurrentInstance();
        if (initAction != null) {
            String newSrc = executeInitAction(context, initAction);
            if (newSrc != null) {
                processInclude(jsfContext, parentTag, newSrc);
                return;
            }
        }

        insertProcessor.process(jsfContext, parentTag);
    }

    protected String executeInitAction(FacesContext context, String initAction) {
        Application app = context.getApplication();
        MethodBinding mb = app.createMethodBinding(initAction, null);
        try {
            String outcome = InvokeUtil.invoke(mb, context);
            if (outcome == null || context.getResponseComplete()) {
                return null;
            }

            return null;
        } catch (EvaluationException ex) {
            Throwable cause = ex.getCause();
            S2Container container = SingletonS2ContainerFactory.getContainer();
            ErrorPageManager manager = (ErrorPageManager) container
                    .getComponent(ErrorPageManager.class);
            ExternalContext extContext = context.getExternalContext();
            String location = manager.handlePartException(cause, extContext);
            if (location != null) {
                return location;
            }

            throw ex;
        }
    }
}