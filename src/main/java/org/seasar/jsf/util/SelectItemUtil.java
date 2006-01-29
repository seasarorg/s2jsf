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
package org.seasar.jsf.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.seasar.jsf.exception.NoSelectItemRuntimeException;

/**
 * @author higa
 *  
 */
public final class SelectItemUtil {

    private SelectItemUtil() {
    }

    public static List getSelectItemList(UIComponent component) {
        List list = new ArrayList(component.getChildCount());
        for (Iterator children = component.getChildren().iterator(); children
                .hasNext();) {
            UIComponent child = (UIComponent) children.next();
            if (child instanceof UISelectItem) {
                addSelectItem(list, (UISelectItem) child);
            } else if (child instanceof UISelectItems) {
                addSelectItems(list, (UISelectItems) child);
            }
        }
        return list;
    }

    public static void addSelectItem(List list, UISelectItem component) {
        SelectItem selectItem = getSelectItem(component);
        list.add(selectItem);
    }

    public static SelectItem getSelectItem(UISelectItem component) {
        Object value = component.getValue();
        if (value != null) {
            return convertValueAsSelectItem(value, component);
        } else {
            Object itemValue = component.getItemValue();
            String label = component.getItemLabel();
            String description = component.getItemDescription();
            boolean disabled = component.isItemDisabled();
            return createSelectItem(itemValue, label, description, disabled);
        }
    }

    public static SelectItem convertValueAsSelectItem(Object value, UIComponent component) {
        if (value instanceof SelectItem) {
            return (SelectItem) value;
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            String clientId = component.getClientId(context);
            throw new NoSelectItemRuntimeException(clientId);
        }
    }

    public static SelectItem createSelectItem(Object value, String label,
            String description, boolean disabled) {

        if (label == null && value != null) {
            label = value.toString();
        }
        SelectItem selectItem = new SelectItem();
        if (value != null) {
            selectItem.setValue(value);
        }
        if (label != null) {
            selectItem.setLabel(label);
        }
        selectItem.setDescription(description);
        selectItem.setDisabled(disabled);
        return selectItem;
    }

    public static void addSelectItems(List list, UISelectItems component) {
        Object value = component.getValue();
        if (value instanceof SelectItem) {
            list.add(value);
        } else if (value instanceof SelectItem[]) {
            SelectItem[] items = (SelectItem[]) value;
            for (int i = 0; i < items.length; i++) {
                list.add(items[i]);
            }
        } else if (value instanceof Collection) {
            for (Iterator it = ((Collection) value).iterator(); it.hasNext();) {
                Object item = it.next();
                list.add(convertValueAsSelectItem(item, component));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            String clientId = component.getClientId(context);
            throw new NoSelectItemRuntimeException(clientId);
        }
    }
}