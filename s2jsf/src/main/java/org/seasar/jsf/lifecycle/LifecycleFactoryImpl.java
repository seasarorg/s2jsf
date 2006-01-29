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
package org.seasar.jsf.lifecycle;

import javax.faces.FacesException;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import org.seasar.jsf.exception.LifecycleIdAlreadyExistRuntimeException;
import org.seasar.jsf.exception.LifecycleIdNotFoundRuntimeException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LifecycleFactoryImpl extends LifecycleFactory {

	private Map lifecycles = Collections.synchronizedMap(new HashMap());

	public LifecycleFactoryImpl() {
		addLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE, new LifecycleImpl());
	}

	public void addLifecycle(String id, Lifecycle lifecycle) {
		if (lifecycles.put(id, lifecycle) != null) {
			throw new LifecycleIdAlreadyExistRuntimeException(id);
		}
	}

	public Lifecycle getLifecycle(String id) throws FacesException {
		Lifecycle lifecycle = (Lifecycle) lifecycles.get(id);
		if (lifecycle == null) {
			throw new LifecycleIdNotFoundRuntimeException(id);
		}
		return lifecycle;
	}

	public Iterator getLifecycleIds() {
		return lifecycles.keySet().iterator();
	}
}