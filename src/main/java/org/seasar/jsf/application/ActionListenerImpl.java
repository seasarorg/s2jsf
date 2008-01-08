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
package org.seasar.jsf.application;

import java.io.IOException;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UICommand;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.log.Logger;
import org.seasar.jsf.ErrorPageManager;
import org.seasar.jsf.util.ExternalContextUtil;
import org.seasar.jsf.util.InvokeUtil;
import org.seasar.jsf.util.UIParameterUtil;

/**
 * @author higa
 * 
 */
public class ActionListenerImpl implements ActionListener {

    private static Logger logger = Logger.getLogger(ActionListenerImpl.class);

    private ErrorPageManager errorPageManager;

    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        HttpServletRequest request = ExternalContextUtil.getRequest(extContext);
        Application app = context.getApplication();
        UICommand command = (UICommand) actionEvent.getComponent();
        UIParameterUtil.saveParamsToRequest(command, request);
        MethodBinding mb = command.getAction();
        String fromAction = null;
        String outcome = null;
        if (mb != null) {
            fromAction = mb.getExpressionString();
            try {
                outcome = InvokeUtil.invoke(mb, context);
            } catch (EvaluationException ex) {
                Throwable cause = ex.getCause();
                ErrorPageManager manager = getErrorPageManager();
                try {
                    if (manager.handleException(cause, extContext)) {
                        context.responseComplete();
                    } else {
                        throw ex;
                    }
                } catch (IOException ioe) {
                    logger.log(ioe);
                    throw ex;
                }

            }
        }
        NavigationHandler navigationHandler = app.getNavigationHandler();
        navigationHandler.handleNavigation(context, fromAction, outcome);
        context.renderResponse();
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