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

import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;

/**
 * @author cero-t
 */
public class MockUIForm extends UIForm {

    private String clientId;

    public String getClientId(FacesContext context) {
        if (clientId != null) {
            return clientId;
        }
        return super.getClientId(context);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}