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
package org.seasar.jsf.processor;

import org.seasar.jsf.JsfConstants;

/**
 * @author higa
 *
 */
public class SelectItemProcessor extends TagProcessorImpl {

	public SelectItemProcessor(String inject) {
		super(inject);
	}
	
	public void renameProperties() {
		super.renameProperties();
		renameProperty(JsfConstants.VALUE_ATTR, JsfConstants.ITEM_VALUE_ATTR);
		renameProperty(JsfConstants.DISABLED_ATTR, JsfConstants.ITEM_DISABLED_ATTR);
	}
}