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
package org.seasar.jsf.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.seasar.framework.log.Logger;

/**
 * @author higa
 *
 */
public class MessageUtil {

    private static final String DETAIL_SUFFIX = "_detail";

    private static final String S2BUNDLE_NAME = "org.seasar.jsf.resources.Messages";

    private static Logger logger_ = Logger.getLogger(MessageUtil.class);

    private MessageUtil() {
    }

    public static FacesMessage getS2ErrorMessage(String messageId,
            Object args[]) {
        return getErrorMessage(messageId, args, S2BUNDLE_NAME);
    }

    public static FacesMessage getErrorMessage(String messageId, Object args[]) {
        return getMessage(messageId, args, FacesMessage.SEVERITY_ERROR);
    }

    public static FacesMessage getErrorMessage(String messageId, Object args[],
            String appBundleName) {

        return getMessage(messageId, args, FacesMessage.SEVERITY_ERROR,
                appBundleName, FacesContext.getCurrentInstance().getViewRoot()
                        .getLocale());
    }

    public static FacesMessage getMessage(String messageId, Object args[],
            FacesMessage.Severity severity) {

        return getMessage(messageId, args, severity, FacesContext
                .getCurrentInstance());
    }

    public static FacesMessage getMessage(String messageId, Object args[],
            FacesMessage.Severity severity, FacesContext context) {

        return getMessage(messageId, args, severity, context.getApplication()
                .getMessageBundle(), context.getViewRoot().getLocale());
    }

    public static FacesMessage getMessage(String messageId, Object args[],
            FacesMessage.Severity severity, String appBundleName, Locale locale) {

        ResourceBundle appBundle = null;
        ResourceBundle defBundle = null;
        String summary = null;
        String detail = null;

        appBundle = getBundle(appBundleName, locale);
        summary = getBundleString(appBundle, messageId);
        if (summary != null) {
            detail = getBundleString(appBundle, messageId + DETAIL_SUFFIX);
        } else {
            defBundle = getBundle(FacesMessage.FACES_MESSAGES, locale);
            summary = getBundleString(defBundle, messageId);
            if (summary != null) {
                detail = getBundleString(defBundle, messageId + DETAIL_SUFFIX);
            } else {
                detail = getBundleString(appBundle, messageId + DETAIL_SUFFIX);
                if (detail != null) {
                    summary = null;
                } else {
                    detail = getBundleString(defBundle, messageId
                            + DETAIL_SUFFIX);
                    if (detail != null) {
                        summary = null;
                    } else {
                        return null;
                    }
                }
            }
        }
        MessageFormat format;
        if (summary != null) {
            format = new MessageFormat(summary, locale);
            summary = format.format(args);
        }
        if (detail != null) {
            format = new MessageFormat(detail, locale);
            detail = format.format(args);
        }
        return new FacesMessage(severity, summary, detail);
    }

    public static String getBundleString(ResourceBundle bundle, String key) {
        try {
            return bundle == null ? null : bundle.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    public static ResourceBundle getBundle(String bundleName, Locale locale) {
        if (bundleName == null) {
            return null;
        }
        try {
            return ResourceBundle.getBundle(bundleName, locale, Thread
                    .currentThread().getContextClassLoader());
        } catch (MissingResourceException ex) {
            logger_.log(ex);
            return null;
        }
    }
}