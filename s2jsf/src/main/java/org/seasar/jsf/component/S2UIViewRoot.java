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
package org.seasar.jsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

/**
 * @author higa
 *  
 */
public class S2UIViewRoot extends UIViewRoot {

	private int uniqueIdCounter = 0;

	private List events = null;

	public S2UIViewRoot() {
	}

	public void queueEvent(FacesEvent event) {
		if (event == null) {
			throw new NullPointerException("event");
		}
		if (events == null) {
			events = new ArrayList();
		}
		events.add(event);
	}

	public void processDecodes(FacesContext context) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		super.processDecodes(context);
		broadcastForPhase(PhaseId.APPLY_REQUEST_VALUES);
		if (context.getRenderResponse() || context.getResponseComplete()) {
			clearEvents();
		}
	}

	public void processValidators(FacesContext context) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		super.processValidators(context);
		broadcastForPhase(PhaseId.PROCESS_VALIDATIONS);
		if (context.getRenderResponse() || context.getResponseComplete()) {
			clearEvents();
		}
	}

	public void processUpdates(FacesContext context) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		super.processUpdates(context);
		broadcastForPhase(PhaseId.UPDATE_MODEL_VALUES);
		if (context.getRenderResponse() || context.getResponseComplete()) {
			clearEvents();
		}
	}

	public void processApplication(FacesContext context) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		broadcastForPhase(PhaseId.INVOKE_APPLICATION);
		if (context.getRenderResponse() || context.getResponseComplete()) {
			clearEvents();
		}
	}

	public void encodeBegin(FacesContext context) throws IOException {
		uniqueIdCounter = 0;
		clearEvents();
		super.encodeBegin(context);
	}

	public String createUniqueId() {
		return UNIQUE_ID_PREFIX + uniqueIdCounter++;
	}

	protected void broadcastForPhase(PhaseId phaseId) {
		if (events == null) {
			return;
		}
		int phaseIdOrdinal = phaseId.getOrdinal();
		for (Iterator i = events.iterator(); i.hasNext();) {
			FacesEvent event = (FacesEvent) i.next();
			int ordinal = event.getPhaseId().getOrdinal();
			if (ordinal == PhaseId.ANY_PHASE.getOrdinal()
					|| ordinal == phaseIdOrdinal) {
				UIComponent source = event.getComponent();
				try {
					try {
						source.broadcast(event);
					} finally {
						i.remove();
					}
				} catch (AbortProcessingException e) {
					clearEvents();
					break;
				}
			}
		}
	}

	protected void clearEvents() {
		events = null;
	}

	public int getEventSize() {
		if (events != null) {
			return events.size();
		}
		return 0;
	}
}