/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.BindingUtil;
import org.seasar.jsf.util.RenderUtil;

/**
 * @author higa
 *  
 */
public class ForEach extends UIComponentBase implements NamingContainer {

	public static final String COMPONENT_TYPE = "org.seasar.jsf.ForEach";

	public static final String COMPONENT_FAMILY = "org.seasar.jsf.Logic";
	
	private static final Object[] EMPTY_ROWS = new Object[0];

	private String var;

	private String varIndex;

	private int rowIndex;
	
	private int rowCount;
	
	private Object[] rows = EMPTY_ROWS;

	private Map descendantComponentStates = new HashMap();

	public ForEach() {
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getVarIndex() {
		return varIndex;
	}

	public void setVarIndex(String varIndex) {
		this.varIndex = varIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}
	
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext(); 
		Map requestMap = extContext.getRequestMap();
		if (var != null) {
			requestMap.put(var, getCurrentRow());
		}
		if (varIndex != null) {
			requestMap.put(varIndex, new Integer(rowIndex));
		}
	}
	
	protected void removeVarAndVarIndex() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext(); 
		Map requestMap = extContext.getRequestMap();
		if (var != null) {
			requestMap.remove(var);
		}
		if (varIndex != null) {
			requestMap.remove(varIndex);
		}
	}
	
	public Object getCurrentRow() {
		return rows[rowIndex];
	}
		

	protected void setupRows() {
		Object items = BindingUtil.getBindingValue(this,
				JsfConstants.ITEMS_ATTR);
		if (items == null) {
			rows = new Object[rowCount];
		} else if (items instanceof Collection) {
			rows = new ArrayList((Collection) items).toArray();
		} else if (items.getClass().isArray()) {
			rows =  (Object[]) items;
		} else {
			throw new IllegalStateException(JsfConstants.ITEMS_ATTR);
		}
	}

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public void queueEvent(FacesEvent event) {
		super.queueEvent(new ForEachEvent(event, getRowIndex(), this));
	}

	public void broadcast(FacesEvent event) throws AbortProcessingException {
		if (event instanceof ForEachEvent) {
		    setupRows();
			ForEachEvent fee = (ForEachEvent) event;
			setRowIndex(fee.getRowIndex());
			FacesEvent original = fee.getOriginal();
			original.getComponent().broadcast(original);
		} else {
			super.broadcast(event);
		}
	}
	
	public void processDecodes(FacesContext context) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		if (!isRendered()) {
			return;
		}
		setupRows();
		for (int i = 0; i < rows.length; ++i) {
			setRowIndex(i);
			restoreDescendantComponentStates(context, this);
			super.processDecodes(context);
			saveDescendantComponentStates(context, this);
			removeVarAndVarIndex();
		}
    }
	
	public void processValidators(FacesContext context) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		if (!isRendered()) {
			return;
		}
		setupRows();
		for (int i = 0; i < rows.length; ++i) {
			setRowIndex(i);
			restoreDescendantComponentStates(context, this);
			super.processValidators(context);
			saveDescendantComponentStates(context, this);
			removeVarAndVarIndex();
		}
    }
	
	public void processUpdates(FacesContext context) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		if (!isRendered()) {
			return;
		}
		setupRows();
		for (int i = 0; i < rows.length; ++i) {
			setRowIndex(i);
			restoreDescendantComponentStates(context, this);
			super.processUpdates(context);
			saveDescendantComponentStates(context, this);
			removeVarAndVarIndex();
		}
    }

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeBegin(FacesContext context) throws IOException {
		if (context == null) {
			throw new NullPointerException("context");
		}
		if (!isRendered()) {
			return;
		}
	}

	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		if (!isRendered()) {
			return;
		}
		setupRows();
		rowCount = rows.length;
		for (int i = 0; i < rows.length; ++i) {
			setRowIndex(i);
			refreshDescendantComponentClientId(context, this);
			restoreDescendantComponentStates(context, this);
			RenderUtil.encodeChildren(context, this);
			saveDescendantComponentStates(context, this);
			removeVarAndVarIndex();
		}
	}

	protected void saveDescendantComponentStates(FacesContext context,
			UIComponent component) {
		
		List children = component.getChildren();
		for (int i = 0; i < children.size(); ++i) {
			UIComponent child = (UIComponent) children.get(i);
			if (child instanceof ForEach) {
				continue;
			}
			if (child instanceof EditableValueHolder) {
				EditableValueHolder evh = (EditableValueHolder) child;
				descendantComponentStates.put(child.getClientId(context),
						new EditableValueHolderState(evh));
			}
			saveDescendantComponentStates(context, child);
		}
	}
	
	protected void restoreDescendantComponentStates(FacesContext context,
			UIComponent component) {
		
		List children = component.getChildren();
		for (int i = 0; i < children.size(); ++i) {
			UIComponent child = (UIComponent) children.get(i);
			child.setId(child.getId());
			if (child instanceof ForEach) {
				continue;
			}
			if (child instanceof EditableValueHolder) {
				EditableValueHolder evh = (EditableValueHolder) child;
				EditableValueHolderState state = (EditableValueHolderState) descendantComponentStates.get(child.getClientId(context));
				if (state != null) {
					state.restore(evh);
				}
				/*
				String clientId = child.getClientId(context);
				String namingContainerId = 
					clientId.substring(0,
							clientId.lastIndexOf( NamingContainer.SEPARATOR_CHAR));			
				if (namingContainerId.equals(getClientId(context)) ){				
					EditableValueHolder evh = (EditableValueHolder) child;
					EditableValueHolderState state = (EditableValueHolderState) descendantComponentStates.get(child.getClientId(context));
					state.restore(evh);
				}
				*/
			}
			restoreDescendantComponentStates(context, child);
		}
	}
	
	protected void refreshDescendantComponentClientId(FacesContext context,
			UIComponent component) {
		
		List children = component.getChildren();
		for (int i = 0; i < children.size(); ++i) {
			UIComponent child = (UIComponent) children.get(i);
			child.setId(child.getId());
			refreshDescendantComponentClientId(context, child);
		}
	}

	public void encodeEnd(FacesContext context) throws IOException {
	}

	public Object saveState(FacesContext context) {
		Object[] values = new Object[5];
		values[0] = super.saveState(context);
		values[1] = var;
		values[2] = varIndex;
		values[3] = descendantComponentStates;
		values[4] = new Integer(rowCount);
		return values;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		var = (String) values[1];
		varIndex = (String) values[2];
		descendantComponentStates = (Map) values[3];
		rowCount = ((Integer) values[4]).intValue();
	}

	public String getClientId(FacesContext context) {
		String clientId = super.getClientId(context);
		int rowIndex = getRowIndex();
		if (rowIndex == -1) {
			return clientId;
		}
		return clientId + "_" + rowIndex;
	}

	private static class EditableValueHolderState implements Serializable {

		private static final long serialVersionUID = 1L;

        private Object localValue;

		private boolean localValueSet;

		private boolean valid;

		private Object submittedValue;

		public EditableValueHolderState(EditableValueHolder evh) {
			localValue = evh.getLocalValue();
			localValueSet = evh.isLocalValueSet();
			valid = evh.isValid();
			submittedValue = evh.getSubmittedValue();
		}

		public void restore(EditableValueHolder evh) {
			evh.setValue(localValue);
			evh.setLocalValueSet(localValueSet);
			evh.setValid(valid);
			evh.setSubmittedValue(submittedValue);
		}
	}

	private static class ForEachEvent extends FacesEvent {
        
        private static final long serialVersionUID = 1L;

		private FacesEvent original;

		private int rowIndex;

		public ForEachEvent(FacesEvent original, int rowIndex, ForEach forEach) {

			super(forEach);
			this.original = original;
			this.rowIndex = rowIndex;
		}

		public PhaseId getPhaseId() {
			return original.getPhaseId();
		}

		public void setPhaseId(PhaseId phaseId) {
			original.setPhaseId(phaseId);
		}

		public void queue() {
			original.queue();
		}

		public String toString() {
			return original.toString();
		}

		public boolean isAppropriateListener(FacesListener listener) {
			return original.isAppropriateListener(listener);
		}

		public void processListener(FacesListener listener) {
			original.processListener(listener);
		}

		public FacesEvent getOriginal() {
			return original;
		}

		public int getRowIndex() {
			return rowIndex;
		}
	}
}