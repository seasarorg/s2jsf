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
package org.seasar.jsf.mock;

import java.io.IOException;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * @author cero-t
 */
public class MockViewHandler extends ViewHandler {
    private String actionURL;

    public Locale calculateLocale(FacesContext context) {
        return null;
    }

    public String calculateRenderKitId(FacesContext context) {
        return null;
    }

    public UIViewRoot createView(FacesContext context, String viewId) {
        return null;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }

    public String getActionURL(FacesContext context, String viewId) {
        return actionURL;
    }

    public String getResourceURL(FacesContext context, String path) {
        return null;
    }

    public void renderView(FacesContext context, UIViewRoot viewToRender)
            throws IOException, FacesException {
    }

    public UIViewRoot restoreView(FacesContext context, String viewId) {
        return null;
    }

    public void writeState(FacesContext context) throws IOException {
    }
}
