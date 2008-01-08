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
package org.seasar.jsf.util;

import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.component.UIParameter;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author cero-t
 */
public class UIParameterUtilTest extends TeedaTestCase {

    public void testSaveParamsToRequest() throws Exception {
        // # Arrange #
        getRequest().addParameter("aaa", "1");
        getRequest().addParameter("bbb", "2");

        UICommand command = new UICommand();
        List childrenList = command.getChildren();
        UIParameter parameter1 = new UIParameter();
        parameter1.setName("aaa");
        parameter1.setValue("#{e.aaa}");
        childrenList.add(parameter1);
        UIParameter parameter2 = new UIParameter();
        parameter2.setName("bbb");
        parameter2.setValue("#{e.bbb + 1}");
        childrenList.add(parameter2);

        assertEquals(null, getRequest().getAttribute("aaa"));
        assertEquals(null, getRequest().getAttribute("bbb"));

        // # Act #
        UIParameterUtil.saveParamsToRequest(command, getRequest());

        // # Assert #
        assertEquals("1", getRequest().getAttribute("aaa"));
        assertEquals("2", getRequest().getAttribute("bbb"));
    }
}
