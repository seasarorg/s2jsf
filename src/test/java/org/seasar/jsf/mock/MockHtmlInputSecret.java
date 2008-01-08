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

import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

/**
 * @author yone
 */
public class MockHtmlInputSecret extends HtmlInputSecret {

    private Renderer renderer;

    private String clientId;

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    protected Renderer getRenderer(FacesContext context) {
        if (renderer != null) {
            return renderer;
        }
        return super.getRenderer(context);
    }

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