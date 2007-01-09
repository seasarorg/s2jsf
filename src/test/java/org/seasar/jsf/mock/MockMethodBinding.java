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
package org.seasar.jsf.mock;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

/**
 * @author yone
 */
public class MockMethodBinding extends MethodBinding {

    private String expressionString;

    public String getExpressionString() {
        return expressionString;
    }

    public void setExpressionString(String expressionString) {
        this.expressionString = expressionString;
    }

    public Class getType(FacesContext context) throws MethodNotFoundException {
        return null;
    }

    public Object invoke(FacesContext context, Object[] params) {
        return null;
    }

}
