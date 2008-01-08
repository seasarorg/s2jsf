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
package org.seasar.jsf.exception;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.NoHttpSessionRuntimeException;

/**
 * @author higa
 *
 */
public class NoHttpSessionRuntimeExceptionTest extends S2TestCase {

	public NoHttpSessionRuntimeExceptionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(NoHttpSessionRuntimeExceptionTest.class);
	}
	
	public void testGetMessage() throws Exception {
		NoHttpSessionRuntimeException ex = new NoHttpSessionRuntimeException();
		System.out.println(ex.getMessage());
	}
}