package examples.jsf.interceptor;

import javax.faces.context.FacesContext;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.ThrowsInterceptor;
import org.seasar.jsf.util.MessageUtil;

import examples.jsf.exception.AppRuntimeException;

/**
 * @author higa
 *
 */
public class ActionThrowsInterceptor extends ThrowsInterceptor {
	
	public String handleThrowable(AppRuntimeException ex, MethodInvocation invocation) throws Throwable {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, MessageUtil.getErrorMessage(ex.getMessageId(), ex.getArgs()));
		return null;
	}

}
