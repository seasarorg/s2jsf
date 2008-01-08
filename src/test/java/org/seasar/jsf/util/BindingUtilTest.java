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

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.jsf.util.BindingUtil;

/**
 * @author higa
 *  
 */
public class BindingUtilTest extends S2TestCase {
	
	public void testIsValueReference() throws Exception {
		assertEquals("1", true, BindingUtil.isValueReference("#{hoge.aaa}"));
		assertEquals("2", false, BindingUtil.isValueReference("${hoge.aaa}"));
	}

    public void testGetValue() throws Exception {
        MockHttpServletRequest mockRequest = getRequest();
        mockRequest.setAttribute("aaa", null);
        mockRequest.setAttribute("bbb", "BBB");
        mockRequest.setParameter("aaa", "AAA");
        assertEquals("AAA", BindingUtil.getValue(mockRequest, "aaa"));
        assertEquals("BBB", BindingUtil.getValue(mockRequest, "bbb"));
    }
}