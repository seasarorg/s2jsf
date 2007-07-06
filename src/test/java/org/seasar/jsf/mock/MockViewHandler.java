package org.seasar.jsf.mock;

import java.io.IOException;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class MockViewHandler extends ViewHandler {
    private String actionURL;

    public Locale calculateLocale(FacesContext context) {
        // TODO Auto-generated method stub
        return null;
    }

    public String calculateRenderKitId(FacesContext context) {
        // TODO Auto-generated method stub
        return null;
    }

    public UIViewRoot createView(FacesContext context, String viewId) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }

    public String getActionURL(FacesContext context, String viewId) {
        return actionURL;
    }

    public String getResourceURL(FacesContext context, String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public void renderView(FacesContext context, UIViewRoot viewToRender)
            throws IOException, FacesException {
        // TODO Auto-generated method stub

    }

    public UIViewRoot restoreView(FacesContext context, String viewId) {
        // TODO Auto-generated method stub
        return null;
    }

    public void writeState(FacesContext context) throws IOException {
        // TODO Auto-generated method stub

    }

}
