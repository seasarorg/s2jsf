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
package org.seasar.jsf.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.JsfContext;
import org.seasar.jsf.TagProcessor;
import org.seasar.jsf.ViewTemplate;
import org.seasar.jsf.ViewTemplateFactory;
import org.seasar.jsf.exception.InsertProcessorNotFoundRuntimeException;
import org.seasar.jsf.util.BindingUtil;

/**
 * @author higa
 * @author shot
 * 
 */
public class ViewProcessor extends TagProcessorImpl {

    private static final String TAG = "view";

    private String contentType;

    private String encoding;

    private String extendsPath;

    private String initAction;

    private List insertProcessors = new ArrayList();

    private ViewTemplateFactory viewTemplateFactory;

    public ViewProcessor() {
    }

    public ViewProcessor(JsfConfig jsfConfig,
            ViewTemplateFactory viewTemplateFactory) {
        setProperty(JsfConstants.INJECT_ATTR, jsfConfig
                .getTaglibPrefix(JsfConstants.JSF_CORE_URI)
                + ":" + TAG);
        processInject(jsfConfig);
        this.viewTemplateFactory = viewTemplateFactory;
    }

    public String getContentType() {
        ViewProcessor parent = getExtendsViewProcessor();
        if (parent != null) {
            return parent.getContentType();
        }

        if (!BindingUtil.isValueReference(contentType)) {
            return contentType;
        }
        return (String) BindingUtil.resolveBinding(contentType);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
        if (!BindingUtil.isValueReference(contentType)) {
            encoding = calcEncoding(contentType);
        }
    }

    protected String calcEncoding(String ct) {
        if (ct == null) {
            return null;
        }
        int index = ct.indexOf(JsfConstants.CHARSET);
        if (index < 0) {
            throw new IllegalArgumentException(JsfConstants.CHARSET
                    + " not found in " + contentType);
        }
        int index2 = ct.indexOf(JsfConstants.EQUAL, index + 1);
        String s = ct.substring(index2 + 1);
        return s.trim();
    }

    public String getEncoding() {
        ViewProcessor parent = getExtendsViewProcessor();
        if (parent != null) {
            return parent.getEncoding();
        }

        if (encoding != null) {
            return encoding;
        }
        if (BindingUtil.isValueReference(contentType)) {
            return calcEncoding(getContentType());
        }
        return encoding;
    }

    public InsertProcessor getInsertProcessor(String name) {
        for (int i = 0; i < insertProcessors.size(); ++i) {
            InsertProcessor ip = (InsertProcessor) insertProcessors.get(i);
            if (name == null && ip.getName() == null || name != null
                    && name.equalsIgnoreCase(ip.getName())) {
                return ip;
            }
        }
        throw new InsertProcessorNotFoundRuntimeException(name);
    }

    public void addInsertProcessor(InsertProcessor insertProcessor) {
        insertProcessors.add(insertProcessor);
    }

    public String[] getIncludes() {
        Set includes = new HashSet();
        addIncludes(includes);
        return (String[]) includes.toArray(new String[includes.size()]);
    }

    protected void addIncludes(Set includes) {
        for (int i = 0; i < insertProcessors.size(); ++i) {
            InsertProcessor ip = (InsertProcessor) insertProcessors.get(i);
            String[] srcs = ip.getSrcs();
            if (srcs != null) {
                for (int j = 0; j < srcs.length; j++) {
                    includes.add(srcs[j]);
                }
            }
        }
        ViewProcessor extendsViewProcessor = getExtendsViewProcessor();
        if (extendsViewProcessor != null) {
            includes.add(getExtendsPath());
            extendsViewProcessor.addIncludes(includes);
        }
    }

    public String getExtendsPath() {
        if (BindingUtil.isValueReference(extendsPath)) {
            FacesContext context = FacesContext.getCurrentInstance();
            Application app = context.getApplication();
            ValueBinding vb = app.createValueBinding(extendsPath);
            String path = (String) vb.getValue(context);

            if (isPageModified(extendsPath, path)) {
                restructComponentTree();
            }

            return path;
        } else {
            return extendsPath;
        }
    }

    public void setExtendsPath(String extendsPath) {
        this.extendsPath = extendsPath;
    }

    protected ViewProcessor getExtendsViewProcessor() {
        final String path = getExtendsPath();
        if (path == null) {
            return null;
        }
        ViewTemplate vt = viewTemplateFactory.getViewTemplate(path);
        return (ViewProcessor) vt.getRootTagProcessor();
    }

    public String getInitAction() {
        return initAction;
    }

    public void setInitAction(String initAction) {
        this.initAction = initAction;
    }

    public void process(JsfContext jsfContext, Tag parentTag)
            throws JspException {

        // setupParamsInternal(jsfContext.getJsfConfig(),
        // jsfContext.getPageContext()
        // .getRequest());
        ViewProcessor extendsViewProcessor = getExtendsViewProcessor();
        if (extendsViewProcessor != null) {
            setupInsertProcessorMap(jsfContext.getPageContext());
            extendsViewProcessor.process(jsfContext, parentTag);
        } else {
            super.process(jsfContext, parentTag);
        }
    }

    protected void setupInsertProcessorMap(PageContext pageContext) {
        Map insertProcessorMap = getInsertProcessorMap(pageContext);
        for (int i = 0; i < insertProcessors.size(); ++i) {
            InsertProcessor ip = (InsertProcessor) insertProcessors.get(i);
            String name = ip.getName();
            if (name == null || insertProcessorMap.containsKey(name)) {
                continue;
            }
            insertProcessorMap.put(name, ip);
        }
    }

    public void setupParams(JsfConfig jsfConfig, Map params) {
        setupParamsInternal(jsfConfig, params);
        ViewProcessor extendsViewProcessor = getExtendsViewProcessor();
        if (extendsViewProcessor != null) {
            extendsViewProcessor.setupParams(jsfConfig, params);
        }
    }

    protected void setupParamsInternal(JsfConfig jsfConfig, Map params) {
        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        String inject = jsfConfig.getTaglibPrefix(JsfConstants.JSF_CORE_URI)
                + ":param";
        TagProcessor bodyProcessor = findBodyProcessor(this);
        if (bodyProcessor == null) {
            return;
        }
        for (int i = 0; i < bodyProcessor.getChildCount(); ++i) {
            TagProcessor tp = bodyProcessor.getChild(i);
            if (inject.equalsIgnoreCase(tp
                    .getProperty(JsfConstants.INJECT_ATTR))) {
                String name = tp.getProperty(JsfConstants.NAME_ATTR);
                if (params.containsKey(name)) {
                    continue;
                }
                String valstr = tp.getProperty(JsfConstants.VALUE_ATTR);
                Object value = valstr;
                if (BindingUtil.isValueReference(valstr)) {
                    ValueBinding vb = app.createValueBinding(valstr);
                    value = vb.getValue(context);
                }
                params.put(name, value);
            }
        }
    }

    protected TagProcessor findBodyProcessor(TagProcessor tp) {
        for (int i = 0; i < tp.getChildCount(); ++i) {
            TagProcessor child = tp.getChild(i);
            if (child instanceof ElementProcessor) {
                ElementProcessor ep = (ElementProcessor) child;
                if (JsfConstants.BODY_ELEM.equalsIgnoreCase(ep.getTagName())) {
                    return child;
                }
            }
            TagProcessor bp = findBodyProcessor(child);
            if (bp != null) {
                return bp;
            }
        }
        return null;
    }
}