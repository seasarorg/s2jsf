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
package org.seasar.jsf.component.html;

import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockValueBinding;

import junit.framework.TestCase;

/**
 * @author cero-t
 */
public class S2HtmlSelectOneRadioTest extends TestCase {

    public void testValue() throws Exception {
        S2HtmlSelectOneRadio input = new S2HtmlSelectOneRadio();
        input.setValue("");
        MockFacesContext context = new MockFacesContext();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(context, "hoge");
        input.setValueBinding("value", vb);
        Object value = input.getValue();
        assertNull(value);
    }
}
