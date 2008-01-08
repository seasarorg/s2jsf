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
package org.seasar.jsf.render.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.HtmlFormRendererUtil;
import org.seasar.jsf.util.JavascriptUtil;
import org.seasar.jsf.util.RenderUtil;

/**
 * @author manhole
 */
public class HtmlFormRenderer extends Renderer {

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {

        super.encodeBegin(facesContext, component);
        UIForm form = (UIForm) component;

        ResponseWriter writer = facesContext.getResponseWriter();
        ViewHandler viewHandler = facesContext.getApplication()
                .getViewHandler();
        String viewId = facesContext.getViewRoot().getViewId();
        String clientId = form.getClientId(facesContext);
        String actionURL = viewHandler.getActionURL(facesContext, viewId);

        writer.startElement(JsfConstants.FORM_ELEM, form);
        writer.writeAttribute(JsfConstants.ID_ATTR, clientId, null);
        writer.writeAttribute(JsfConstants.NAME_ATTR, clientId, null);
        writer.writeAttribute(JsfConstants.METHOD_ATTR,
                JsfConstants.POST_VALUE, null);
        writer.writeURIAttribute(JsfConstants.ACTION_ATTR, facesContext
                .getExternalContext().encodeActionURL(actionURL), null);

        RenderUtil.renderAttributes(writer, form,
                JsfConstants.FORM_PASSTHROUGH_ATTRIBUTES);
        writer.write("");
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();

        ViewHandler viewHandler = facesContext.getApplication()
                .getViewHandler();
        viewHandler.writeState(facesContext);

        HtmlForm form = (HtmlForm) component;
        writeSubmittedMarker(facesContext, form, writer);
        writeHidden(facesContext, form, writer);

        writer.endElement(JsfConstants.FORM_ELEM);
    }

    protected void writeSubmittedMarker(FacesContext facesContext, UIForm form,
            ResponseWriter writer) throws IOException {
        writer.startElement(JsfConstants.INPUT_ELEM, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE, null);
        writer.writeAttribute(JsfConstants.NAME_ATTR, HtmlFormRendererUtil
                .createFormIdentifyKey(facesContext, form), null);
        writer.writeAttribute(JsfConstants.VALUE_ATTR,
                JsfConstants.HIDDEN_SUBMIT_INPUT_VALUE, null);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    protected void writeHidden(FacesContext facesContext, HtmlForm form,
            ResponseWriter writer) throws IOException {
        Set set = (Set) form.getAttributes().get(
                JsfConstants.HIDDEN_COMMAND_INPUTS_SET_ATTR);
        if (set != null && !set.isEmpty()) {
            for (Iterator it = set.iterator(); it.hasNext();) {
                writer.startElement(JsfConstants.INPUT_ELEM, null);
                writer.writeAttribute(JsfConstants.TYPE_ATTR,
                        JsfConstants.HIDDEN_VALUE, null);
                writer.writeAttribute(JsfConstants.NAME_ATTR, it.next(), null);
                writer.endElement(JsfConstants.INPUT_ELEM);
            }

            String target = form.getTarget();
            renderClearHiddenCommandFormParamsFunction(writer, form
                    .getClientId(facesContext), set, target);
        }
    }

    protected void renderClearHiddenCommandFormParamsFunction(
            ResponseWriter writer, String formName, Set dummyFormParams,
            String formTarget) throws IOException {

        String functionName = JavascriptUtil
                .getClearHiddenCommandFormParamsFunctionName(formName);
        writer.startElement(JsfConstants.SCRIPT_ELEM, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR, "text/javascript", null);
        writer.write("\n<!--");
        writer.write("\nfunction ");
        writer.write(functionName);
        writer.write("() {");
        if (dummyFormParams != null) {
            writer.write("\n  var f = document.forms['");
            writer.write(formName);
            writer.write("'];");
            for (Iterator it = dummyFormParams.iterator(); it.hasNext();) {
                writer.write("\n  f.elements['");
                writer.write((String) it.next());
                writer.write("'].value='" + JsfConstants.NULL_VALUE + "';");
            }
        }
        writer.write("\n  f.target=");
        if (formTarget == null || formTarget.length() == 0) {
            writer.write("'';");
        } else {
            writer.write("'");
            writer.write(formTarget);
            writer.write("';");
        }
        writer.write("\n}");

        writer.write("\n");
        writer.write(functionName);
        writer.write("();");

        writer.write("\n//-->\n");
        writer.endElement(JsfConstants.SCRIPT_ELEM);
    }

    public void decode(FacesContext facesContext, UIComponent component) {
        super.decode(facesContext, component);
        UIForm form = (UIForm) component;
        if (isSubmitted(facesContext, form)) {
            form.setSubmitted(true);
        } else {
            form.setSubmitted(false);
        }
    }

    protected boolean isSubmitted(FacesContext facesContext, UIForm form) {
        Map paramMap = facesContext.getExternalContext()
                .getRequestParameterMap();
        String formIdentifyKey = HtmlFormRendererUtil.createFormIdentifyKey(
                facesContext, form);
        String submittedValue = (String) paramMap.get(formIdentifyKey);
        final boolean submitted = submittedValue != null
                && submittedValue
                        .equals(JsfConstants.HIDDEN_SUBMIT_INPUT_VALUE);
        return submitted;
    }

}
