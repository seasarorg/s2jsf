package it00011;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.jsf.util.MessageUtil;

public class SomeActionImpl implements SomeAction {

    public String showMessages() {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, MessageUtil.getMessage("fatalMessageId", null,
            FacesMessage.SEVERITY_FATAL, context));
        context.addMessage(null, MessageUtil.getMessage("errorMessageId", null,
            FacesMessage.SEVERITY_ERROR, context));
        context.addMessage(null, MessageUtil.getMessage("warnMessageId", null,
            FacesMessage.SEVERITY_WARN, context));
        context.addMessage(null, MessageUtil.getMessage("infoMessageId", null,
            FacesMessage.SEVERITY_INFO, context));

        return null;
    }

    public String showMessage() {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(getComponentClientId(context, "form:a"), MessageUtil
            .getMessage("fatalMessageId", null, FacesMessage.SEVERITY_FATAL,
                context));
        context.addMessage(getComponentClientId(context, "form:b"), MessageUtil
            .getMessage("errorMessageId", null, FacesMessage.SEVERITY_ERROR,
                context));
        context.addMessage(getComponentClientId(context, "form:c"), MessageUtil
            .getMessage("warnMessageId", null, FacesMessage.SEVERITY_WARN,
                context));
        context.addMessage(getComponentClientId(context, "form:d"), MessageUtil
            .getMessage("infoMessageId", null, FacesMessage.SEVERITY_INFO,
                context));

        return null;
    }

    private String getComponentClientId(FacesContext context, String id) {
        UIViewRoot viewRoot = context.getViewRoot();
        return viewRoot.findComponent(id).getClientId(context);
    }

}
