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
package org.seasar.jsf.el;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.jsf.util.BindingUtil;

/**
 * @author higa
 *
 */
public class S2VariableResolver extends VariableResolver {

	private static final String APPLICATION_SCOPE = "applicationScope";

	private static final String COOKIE = "cookie";

	private static final String FACES_CONTEXT = "facesContext";

	private static final String HEADER = "header";

	private static final String HEADER_VALUES = "headerValues";

	private static final String INIT_PARAM = "initParam";

	private static final String PARAM = "param";

	private static final String PARAM_VALUES = "paramValues";

	private static final String REQUEST_SCOPE = "requestScope";

	private static final String SESSION_SCOPE = "sessionScope";

	private static final String VIEW = "view";

	private static Map facesResolvers = new HashMap();

	static {
		facesResolvers.put(APPLICATION_SCOPE, new ApplicationScopeResolver());
		facesResolvers.put(COOKIE, new CookieResolver());
		facesResolvers.put(FACES_CONTEXT, new FacesContextResolver());
		facesResolvers.put(HEADER, new HeaderResolver());
		facesResolvers.put(HEADER_VALUES, new HeaderValuesResolver());
		facesResolvers.put(INIT_PARAM, new InitParamResolver());
		facesResolvers.put(PARAM, new ParamResolver());
		facesResolvers.put(PARAM_VALUES, new ParamValuesResolver());
		facesResolvers.put(REQUEST_SCOPE, new RequestScopeResolver());
		facesResolvers.put(SESSION_SCOPE, new SessionScopeResolver());
		facesResolvers.put(VIEW, new ViewResolver());
	}

	public S2VariableResolver() {
	}

	/**
	 * @see javax.faces.el.VariableResolver#resolveVariable(javax.faces.context.FacesContext,
	 *      java.lang.String)
	 */
	public Object resolveVariable(FacesContext context, String name)
			throws EvaluationException {

		FacesResolver resolver = (FacesResolver) facesResolvers.get(name);
		if (resolver != null) {
			return resolver.resolveVariable(context);
		}
		S2Container container = SingletonS2ContainerFactory.getContainer();
		return BindingUtil.getValue(container, name);
	}
}