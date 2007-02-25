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
package org.seasar.jsf.lifecycle;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.jsf.ErrorPageManager;
import org.seasar.jsf.component.ForEach;
import org.seasar.jsf.component.S2UIViewRoot;
import org.seasar.jsf.util.ExternalContextUtil;

public class LifecycleImpl extends Lifecycle {

    private static final String VIEW_ID_ATTR = LifecycleImpl.class.getName()
            + ".VIEW_ID";

    private static final String EXECUTED_ATTR = LifecycleImpl.class.getName()
            + ".EXECUTED";

    private static final String REDIRECTED_TIME_ATTR = LifecycleImpl.class
            .getName()
            + ".REDIRECTED_TIME";

    private static final long REDIRECT_WAIT_TIME = 2000;

    private static final String POSTBACK_ATTR = "postback";

    private PhaseListener[] phaseListeners = new PhaseListener[0];

    private ErrorPageManager errorPageManager;

    public LifecycleImpl() {
    }

    public void execute(FacesContext context) throws FacesException {
        try {
            boolean postback = restoreView(context);
            context.getExternalContext().getRequestMap().put(POSTBACK_ATTR,
                    Boolean.valueOf(postback));
            if (isFinished(context)) {
                return;
            }
            ExternalContext extContext = context.getExternalContext();
            Map sessionMap = extContext.getSessionMap();
            Long redirectedTime = (Long) sessionMap.get(REDIRECTED_TIME_ATTR);
            if (redirectedTime != null) {
                sessionMap.remove(REDIRECTED_TIME_ATTR);
                if (System.currentTimeMillis() - redirectedTime.longValue() < REDIRECT_WAIT_TIME) {
                    context.renderResponse();
                    return;
                }
            }
            Map requestMap = extContext.getRequestMap();
            if (requestMap.containsKey(EXECUTED_ATTR)) {
                context.renderResponse();
                return;
            }
            requestMap.put(EXECUTED_ATTR, EXECUTED_ATTR);
            applyRequestValues(context);
            if (isFinished(context)) {
                initializeChildren(context, context.getViewRoot());
                return;
            }
            if (postback || hasEvent(context)) {
                processValidations(context);
                if (isFinished(context)) {
                    return;
                }
            }
            updateModelValues(context);
            if (isFinished(context)) {
                return;
            }
            invokeApplication(context);
            if (isGetRedirect(context)) {
                sessionMap.put(REDIRECTED_TIME_ATTR, new Long(System
                        .currentTimeMillis()));
            }
        } catch (EvaluationException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw ex;
            }
        } catch (Exception ex) {
            handleException(context, ex);
        }
    }

    protected boolean restoreView(FacesContext context) throws FacesException {
        beforePhase(context, PhaseId.RESTORE_VIEW);
        ExternalContext externalContext = context.getExternalContext();
        String viewId = ExternalContextUtil.getViewId(externalContext);
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.restoreView(context, viewId);
        if (viewRoot == null) {
            viewRoot = viewHandler.createView(context, viewId);
            // context.renderResponse();
        }
        String previousViewId = getViewIdFromSession(externalContext);
        context.setViewRoot(viewRoot);
        saveViewIdToSession(externalContext, viewId);
        initializeChildren(context, viewRoot);
        if (externalContext.getRequestParameterMap().isEmpty()) {
            context.renderResponse();
        }
        afterPhase(context, PhaseId.RESTORE_VIEW);
        return viewId.equals(previousViewId);
    }

    protected void applyRequestValues(FacesContext context)
            throws FacesException {

        beforePhase(context, PhaseId.APPLY_REQUEST_VALUES);
        context.getViewRoot().processDecodes(context);
        afterPhase(context, PhaseId.APPLY_REQUEST_VALUES);
    }

    protected void processValidations(FacesContext context)
            throws FacesException {

        beforePhase(context, PhaseId.PROCESS_VALIDATIONS);
        context.getViewRoot().processValidators(context);
        afterPhase(context, PhaseId.PROCESS_VALIDATIONS);
    }

    protected void updateModelValues(FacesContext context)
            throws FacesException {

        beforePhase(context, PhaseId.UPDATE_MODEL_VALUES);
        context.getViewRoot().processUpdates(context);
        afterPhase(context, PhaseId.UPDATE_MODEL_VALUES);
    }

    protected void invokeApplication(FacesContext context)
            throws FacesException {

        beforePhase(context, PhaseId.INVOKE_APPLICATION);
        context.getViewRoot().processApplication(context);
        afterPhase(context, PhaseId.INVOKE_APPLICATION);
    }

    protected boolean isGetRedirect(FacesContext context) {
        if (!context.getResponseComplete()) {
            return false;
        }
        ExternalContext extContext = context.getExternalContext();
        HttpServletRequest request = ExternalContextUtil.getRequest(extContext);
        return request.getMethod().equals("GET");
    }

    public void render(FacesContext context) throws FacesException {
        if (context.getResponseComplete()) {
            return;
        }
        beforePhase(context, PhaseId.RENDER_RESPONSE);
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        try {
            viewHandler.renderView(context, context.getViewRoot());
        } catch (IOException e) {
            throw new FacesException(e.getMessage(), e);
        } catch (EvaluationException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw ex;
            }
        }
        afterPhase(context, PhaseId.RENDER_RESPONSE);
    }

    protected String getViewIdFromSession(ExternalContext externalContext) {
        return (String) externalContext.getSessionMap().get(VIEW_ID_ATTR);
    }

    protected void saveViewIdToSession(ExternalContext externalContext,
            String viewId) {
        externalContext.getSessionMap().put(VIEW_ID_ATTR, viewId);
    }

    protected void initializeChildren(FacesContext context,
            UIComponent component) {
        for (Iterator it = component.getFacetsAndChildren(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();
            if (child instanceof EditableValueHolder) {
                EditableValueHolder editableValueHolder = (EditableValueHolder) child;
                editableValueHolder.setValid(true);
                editableValueHolder.setSubmittedValue(null);
                editableValueHolder.setValue(null);
                editableValueHolder.setLocalValueSet(false);
            } else if (child instanceof ForEach) {
                ForEach forEach = (ForEach) child;
                forEach.initializeChildren(context);
            }
            initializeChildren(context, child);
        }
    }

    protected boolean isFinished(FacesContext context) throws FacesException {
        return context.getResponseComplete() || context.getRenderResponse();
    }

    public void addPhaseListener(PhaseListener listener) {
        phaseListeners = (PhaseListener[]) ArrayUtil.add(phaseListeners,
                listener);
    }

    public void removePhaseListener(PhaseListener listener) {
        phaseListeners = (PhaseListener[]) ArrayUtil.remove(phaseListeners,
                listener);
    }

    public PhaseListener[] getPhaseListeners() {
        return phaseListeners;
    }

    protected void beforePhase(FacesContext context, PhaseId phaseId) {
        for (int i = 0; i < phaseListeners.length; i++) {
            PhaseListener listener = phaseListeners[i];
            if (isTargetListener(listener, phaseId)) {
                listener.beforePhase(new PhaseEvent(context, phaseId, this));
            }
        }
    }

    protected void afterPhase(FacesContext context, PhaseId phaseId) {
        for (int i = phaseListeners.length - 1; 0 <= i; i--) {
            PhaseListener listener = phaseListeners[i];
            if (isTargetListener(listener, phaseId)) {
                listener.afterPhase(new PhaseEvent(context, phaseId, this));
            }
        }
    }

    protected boolean isTargetListener(PhaseListener listener, PhaseId phaseId) {
        int listenerOrdinal = listener.getPhaseId().getOrdinal();
        return listenerOrdinal == PhaseId.ANY_PHASE.getOrdinal()
                || listenerOrdinal == phaseId.getOrdinal();
    }

    protected boolean hasEvent(FacesContext context) {
        S2UIViewRoot viewRoot = (S2UIViewRoot) context.getViewRoot();
        return viewRoot.getEventSize() > 0;
    }

    protected void handleException(FacesContext context, Throwable exception) {
        ErrorPageManager manager = getErrorPageManager();
        try {
            if (manager
                    .handleException(exception, context.getExternalContext())) {
                context.responseComplete();
            } else {
                throw (RuntimeException) exception;
            }
        } catch (IOException ioe) {
            throw new FacesException(ioe.getMessage(), ioe);
        }
    }

    protected ErrorPageManager getErrorPageManager() {
        if (errorPageManager != null) {
            return errorPageManager;
        }
        S2Container container = SingletonS2ContainerFactory.getContainer();
        errorPageManager = (ErrorPageManager) container
                .getComponent(ErrorPageManager.class);
        return errorPageManager;
    }
}
