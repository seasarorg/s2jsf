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
package org.seasar.jsf.render.html;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.jsf.JsfConfig;
import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.exception.ParentTagNotFoundRuntimeException;
import org.seasar.jsf.util.JavascriptUtil;
import org.seasar.jsf.util.RenderUtil;

public class HtmlCommandLinkRenderer extends AbstractHtmlLinkRenderer {

    private static final String HIDDEN_SUBMIT_INPUT_SUFFIX = "_SUBMIT";
    private static final String HIDDEN_SUBMIT_INPUT_VALUE = "1";
    private static final String URL_STATE_MARKER = "JSF_URL_STATE_MARKER=DUMMY";
    private static final String HIDDEN_COMMANDLINK_FIELD_NAME = "_link_hidden_";
    private static final String HIDDEN_COMMAND_INPUTS_SET_ATTR = "org.apache.myfaces.renderkit.html.HtmlFormRendererBase.HIDDEN_COMMAND_INPUTS_SET";

    public void decode(FacesContext facesContext, UIComponent component) {
        super.decode(facesContext, component);

        if (isLinkClicked(facesContext, component)) {
            component.queueEvent(new ActionEvent(component));
        }
    }

    private boolean isLinkClicked(FacesContext facesContext,
        UIComponent component) {

        String formName = getParentFormName(facesContext, component);
        String hiddenCommandLinkFieldName = getHiddenCommandLinkFieldName(formName);
        String reqValue = (String) facesContext.getExternalContext()
            .getRequestParameterMap().get(hiddenCommandLinkFieldName);

        String clientId = component.getClientId(facesContext);
        return reqValue != null && reqValue.equals(clientId);
    }

    public void encodeBegin(FacesContext facesContext, UIComponent component)
        throws IOException {
        super.encodeBegin(facesContext, component);

        renderCommandLinkStart(facesContext, (HtmlCommandLink) component);
    }

    protected void renderCommandLinkStart(FacesContext facesContext,
        HtmlCommandLink link) throws IOException {

        String clientId = link.getClientId(facesContext);

        ResponseWriter writer = facesContext.getResponseWriter();
        if (isJavascriptAllowed(facesContext)) {
            renderJavaScriptAnchorStart(facesContext, writer, link, clientId);
        } else {
            renderNonJavaScriptAnchorStart(facesContext, writer, link, clientId);
        }

        writer.writeAttribute(JsfConstants.ID_ATTR, clientId, null);
        RenderUtil.renderAttributes(writer, link,
            JsfConstants.ANCHOR_PASSTHROUGH_ATTRIBUTES_WITHOUT_STYLE);
        String style = link.getStyle();
        RenderUtil.renderAttribute(writer, JsfConstants.STYLE_ATTR,
            JsfConstants.STYLE_ATTR, style);
        String styleClass = link.getStyleClass();
        RenderUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
            JsfConstants.STYLE_CLASS_ATTR, styleClass);

        Object value = link.getValue();
        if (value != null) {
            writer.writeText(value.toString(), JsfConstants.VALUE_ATTR);
        }
    }

    protected void renderJavaScriptAnchorStart(FacesContext facesContext,
        ResponseWriter writer, HtmlCommandLink link, String clientId)
        throws IOException {

        UIForm parentForm = findParentForm(link);
        String formName = getParentFormName(facesContext, link);

        StringBuffer onClick = new StringBuffer();

        String commandOnclick = link.getOnclick();
        if (commandOnclick != null) {
            onClick.append(commandOnclick);
            onClick.append(';');
        }

        onClick.append(
            JavascriptUtil
                .getClearHiddenCommandFormParamsFunctionName(formName)).append(
            "();");

        String jsForm = "document.forms['" + formName + "']";

        //add id parameter for decode
        String hiddenFieldName = getHiddenCommandLinkFieldName(formName);
        onClick.append(jsForm);
        onClick.append(".elements['").append(hiddenFieldName).append("']");
        onClick.append(".value='").append(clientId).append("';");
        addHiddenCommandParameter(parentForm, hiddenFieldName);

        for (Iterator it = link.getChildren().iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();
            if (child instanceof UIParameter) {
                String name = ((UIParameter) child).getName();
                Object value = ((UIParameter) child).getValue();

                renderLinkParameter(name, value, onClick, jsForm, parentForm);
            }
        }

        // target
        String target = link.getTarget();
        if (target != null && target.trim().length() > 0) {
            onClick.append(jsForm);
            onClick.append(".target='");
            onClick.append(target);
            onClick.append("';");
        }

        // onSubmit
        onClick.append("if(" + jsForm + ".onsubmit){" + jsForm
            + ".onsubmit();}");

        //submit
        onClick.append(jsForm);
        onClick.append(".submit();return false;");

        writer.startElement(JsfConstants.ANCHOR_ELEM, link);
        writer.writeURIAttribute(JsfConstants.HREF_ATTR, "#", null);
        writer.writeAttribute(JsfConstants.ONCLICK_ATTR, onClick.toString(),
            null);
    }

    private void addHiddenCommandParameter(UIForm form, String paramName) {
        Set set = (Set) form.getAttributes()
            .get(HIDDEN_COMMAND_INPUTS_SET_ATTR);
        if (set == null) {
            set = new HashSet();
            form.getAttributes().put(HIDDEN_COMMAND_INPUTS_SET_ATTR, set);
        }
        set.add(paramName);
    }

    protected void renderNonJavaScriptAnchorStart(FacesContext facesContext,
        ResponseWriter writer, UIComponent component, String clientId)
        throws IOException {
        ViewHandler viewHandler = facesContext.getApplication()
            .getViewHandler();
        String viewId = facesContext.getViewRoot().getViewId();
        String path = viewHandler.getActionURL(facesContext, viewId);

        StringBuffer hrefBuf = new StringBuffer(path);

        if (path.indexOf('?') == -1) {
            hrefBuf.append('?');
        } else {
            hrefBuf.append('&');
        }

        String formName = getParentFormName(facesContext, component);
        hrefBuf.append(formName + HIDDEN_SUBMIT_INPUT_SUFFIX);
        hrefBuf.append('=');
        hrefBuf.append(HIDDEN_SUBMIT_INPUT_VALUE);

        hrefBuf.append('&');
        String hiddenFieldName = getHiddenCommandLinkFieldName(formName);
        hrefBuf.append(hiddenFieldName);
        hrefBuf.append('=');
        hrefBuf.append(clientId);

        if (component.getChildCount() > 0) {
            addParametersToHref(component, hrefBuf, false, //not the first url parameter
                writer.getCharacterEncoding());
        }

        StateManager stateManager = facesContext.getApplication()
            .getStateManager();
        if (stateManager.isSavingStateInClient(facesContext)) {
            hrefBuf.append("&");
            hrefBuf.append(URL_STATE_MARKER);
        }
        String href = facesContext.getExternalContext().encodeActionURL(
            hrefBuf.toString());
        writer.startElement(JsfConstants.ANCHOR_ELEM, component);
        writer.writeURIAttribute(JsfConstants.HREF_ATTR, facesContext
            .getExternalContext().encodeActionURL(href), null);
    }

    protected void renderLinkParameter(String name, Object value,
        StringBuffer onClick, String jsForm, UIForm parentForm) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }
        onClick.append(jsForm);
        onClick.append(".elements['").append(name).append("']");
        String strParamValue = value != null ? value.toString() : "";
        onClick.append(".value='").append(strParamValue).append("';");

        addHiddenCommandParameter(parentForm, name);
    }

    private String getParentFormName(FacesContext facesContext,
        UIComponent component) {
        UIForm parentForm = findParentForm(component);
        return parentForm.getClientId(facesContext);
    }

    private String getHiddenCommandLinkFieldName(String formName) {
        return formName + NamingContainer.SEPARATOR_CHAR
            + HIDDEN_COMMANDLINK_FIELD_NAME;
    }

    private UIForm findParentForm(UIComponent component) {
        UIComponent parent = component.getParent();
        while (parent != null && !(parent instanceof UIForm)) {
            parent = parent.getParent();
        }
        if (parent == null) {
            throw new ParentTagNotFoundRuntimeException("form");
        }
        return (UIForm) parent;
    }

    private boolean isJavascriptAllowed(FacesContext facesContext) {
        return getJsfConfig().isAllowJavascript();
    }

    private JsfConfig getJsfConfig() {
        return (JsfConfig) SingletonS2ContainerFactory.getContainer()
            .getComponent(JsfConfig.class);
    }

}
