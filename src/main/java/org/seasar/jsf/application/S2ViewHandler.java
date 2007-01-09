/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.jsf.application;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.jsf.ViewRenderer;
import org.seasar.jsf.util.ExternalContextUtil;

/**
 * @author higa
 *
 */
public class S2ViewHandler extends ViewHandler {

	private ViewHandler originalViewHandler;

	private ViewRenderer viewRenderer;

	public S2ViewHandler(ViewHandler originalViewHandler) {
		this.originalViewHandler = originalViewHandler;
	}

	public Locale calculateLocale(FacesContext context) {
		Application app = context.getApplication();
		Iterator locales = context.getExternalContext().getRequestLocales();
		Locale defaultLocale = app.getDefaultLocale();
		while (locales.hasNext()) {
			Locale locale = (Locale) locales.next();
			Iterator supportedLocales = app.getSupportedLocales();
			while (supportedLocales.hasNext()) {
				Locale supportedLocale = (Locale) supportedLocales.next();
				if (isMatchLocale(locale, supportedLocale)) {
					return supportedLocale;
				}
			}
			if (isMatchLocale(locale, defaultLocale)) {
				return defaultLocale;
			}
		}
		return defaultLocale != null ? defaultLocale : Locale.getDefault();
	}
	
	protected boolean isMatchLocale(Locale reqLocale, Locale jsfLocale) {
		if (reqLocale.equals(jsfLocale)) {
			return true;
		}
		return reqLocale.getLanguage().equals(jsfLocale.getLanguage())
				&& StringUtil.isEmpty(jsfLocale.getCountry());
	}

	public String calculateRenderKitId(FacesContext context) {
		String renderKitId = context.getApplication().getDefaultRenderKitId();
		return renderKitId != null ? renderKitId
				: RenderKitFactory.HTML_BASIC_RENDER_KIT;
	}

	public String getActionURL(FacesContext context, String viewId) {
		return originalViewHandler.getActionURL(context, viewId);
	}

	public String getResourceURL(FacesContext context, String path) {
		return originalViewHandler.getResourceURL(context, path);
	}

	public UIViewRoot createView(FacesContext context, String viewId) {
		Locale locale = null;
		String renderKitId = null;
		UIViewRoot viewRoot = context.getViewRoot();
		if (viewRoot != null) {
			locale = viewRoot.getLocale();
			renderKitId = viewRoot.getRenderKitId();
		} else {
			locale = calculateLocale(context);
			renderKitId = calculateRenderKitId(context);
		}
		viewRoot = (UIViewRoot) context.getApplication().createComponent(
				UIViewRoot.COMPONENT_TYPE);
		viewRoot.setViewId(viewId);
		viewRoot.setLocale(locale);
		viewRoot.setRenderKitId(renderKitId);
		return viewRoot;
	}

	public void renderView(FacesContext context, UIViewRoot viewRoot)
			throws IOException {

		ExternalContext externalContext = context.getExternalContext();
		String path = ExternalContextUtil.getViewId(externalContext);
		if (path.equals(viewRoot.getViewId())) {
			HttpServletRequest request = ExternalContextUtil
					.getRequest(externalContext);
			HttpServletResponse response = ExternalContextUtil
					.getResponse(externalContext);
			getViewRenderer().renderView(path, request, response);
		} else {
			externalContext.dispatch(viewRoot.getViewId());
		}
	}

	protected ViewRenderer getViewRenderer() {
		if (viewRenderer == null) {
			S2Container container = SingletonS2ContainerFactory.getContainer();
			viewRenderer = (ViewRenderer) container
					.getComponent(ViewRenderer.class);
		}
		return viewRenderer;
	}

	public UIViewRoot restoreView(FacesContext context, String viewId) {
		Application app = context.getApplication();
        String renderKitId = calculateRenderKitId(context);
        StateManager stateManager = app.getStateManager(); 
        return stateManager.restoreView(context, viewId, renderKitId);
	}

	public void writeState(FacesContext context) throws IOException {
		originalViewHandler.writeState(context);
	}
}