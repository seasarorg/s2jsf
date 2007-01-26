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
package org.seasar.jsf.application;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.FactoryFinder;
import javax.faces.application.StateManager;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.ResponseStateManager;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.jsf.ViewTemplate;
import org.seasar.jsf.ViewTemplateFactory;
import org.seasar.jsf.processor.ViewProcessor;

/**
 * @author higa
 * @author dewa
 *
 */
public class S2StateManager extends StateManager implements Serializable {

    static final long serialVersionUID = 0L;

    /**
      * web.xml の初期化パラメータを使ってセッションに保持するUIViewRootの数を指定することができる
      * （指定しない場合は、UIViewRootが際限なくセッションに溜まってしまう）
      *   
      * 設定例：
      *      
      *   <context-param>
      *      <param-name>org.seasar.jsf.UI_VIEW_ROOT_CACHE_SIZE</param-name>
      *      <param-value>10</param-value>
      *   </context-param>
      *      
      * ※ 初期化パラメータの値には、整数値をセットする
      * ※ 初期化パラメータの推奨値は 10
      */
    public static final String UI_VIEW_ROOT_CACHE_SIZE = "org.seasar.jsf.UI_VIEW_ROOT_CACHE_SIZE";       
    
    private int viewRootCacheSize = 0;
    
    private static final String SERIALIZED_VIEW_ATTR = S2StateManager.class
            .getName()
            + ".SERIALIZED_VIEW";

    private static final String LAST_MODIFIED_ATTR = S2StateManager.class
            .getName()
            + ".LAST_MODIFIED";

    private static final String VIEW_ACCESS_INFO =S2StateManager.class
            .getName()
            + ".VIEW_ACCESS_INFO";
 
    
    private transient RenderKitFactory renderKitFactory;

    private transient ViewTemplateFactory viewTemplateFactory;   
    
    public S2StateManager() {
    }

    public SerializedView saveSerializedView(FacesContext context)
            throws IllegalStateException {

        UIViewRoot viewRoot = context.getViewRoot();
        ExternalContext externalContext = context.getExternalContext();
        saveLastModifiedToSession(externalContext, viewRoot.getViewId());
        Object struct = getTreeStructureToSave(context);
        Object state = getComponentStateToSave(context);
        SerializedView serializedView = new SerializedView(struct, state);
        if (isSavingStateInClient(context)) {
            return serializedView;
        }

        saveSerializedViewToSession(externalContext, viewRoot.getViewId(),
            serializedView);
        
        if (getViewRootCacheSize(context) > 0) {
            updateViewRootCache(context, viewRoot.getViewId());
        }
        
        return null;
    }

    protected void saveLastModifiedToSession(ExternalContext externalContext,
            String viewId) {
        Long lm = new Long(getLastModifiedFromFile(viewId));
        externalContext.getSessionMap().put(LAST_MODIFIED_ATTR + "-" + viewId,
                lm);
    }

    protected long getLastModifiedFromSession(ExternalContext externalContext,
            String viewId) {
        Long lm = (Long) externalContext.getSessionMap().get(
                LAST_MODIFIED_ATTR + "-" + viewId);
        if (lm != null) {
            return lm.longValue();
        }
        return 0;
    }

    protected void removeLastModifiedFromSession(
            ExternalContext externalContext, String viewId) {
        externalContext.getSessionMap().remove(
                LAST_MODIFIED_ATTR + "-" + viewId);
    }

    protected boolean isViewModified(ExternalContext externalContext,
            String viewId) {
        long lastModifiedFromSession = getLastModifiedFromSession(
                externalContext, viewId);
        long lastModifiedFromFile = getLastModifiedFromFile(viewId);
        return lastModifiedFromFile > lastModifiedFromSession;
    }

    protected long getLastModifiedFromFile(String viewId) {
        ViewTemplateFactory vtf = getViewTemplateFactory();
        ViewTemplate vt = vtf.getViewTemplate(viewId);
        long lastModified = vt.getLastModified();
        if (lastModified == 0) {
            return lastModified;
        }
        ViewProcessor vp = (ViewProcessor) vt.getRootTagProcessor();
        String[] includes = vp.getIncludes();
        for (int i = 0; i < includes.length; ++i) {
            vt = vtf.getViewTemplate(includes[i]);
            long lm = vt.getLastModified();
            if (lm > lastModified) {
                lastModified = lm;
            }
        }
        return lastModified;
    }

    protected SerializedView getSerializedViewFromSession(
            ExternalContext externalContext, String viewId) {

        return (SerializedView) externalContext.getSessionMap().get(
                SERIALIZED_VIEW_ATTR + "-" + viewId);
    }

    protected void saveSerializedViewToSession(ExternalContext externalContext,
            String viewId, SerializedView serializedView) {

        externalContext.getSessionMap().put(
                SERIALIZED_VIEW_ATTR + "-" + viewId, serializedView);
    }

    protected void removeSerializedViewFromSession(
            ExternalContext externalContext, String viewId) {

        externalContext.getSessionMap().remove(
                SERIALIZED_VIEW_ATTR + "-" + viewId);
    }

    protected Object getTreeStructureToSave(FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot.isTransient()) {
            return null;
        }
        return buildTreeStructure(viewRoot);
    }

    protected Object getComponentStateToSave(FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot.isTransient()) {
            return null;
        }
        return viewRoot.processSaveState(context);
    }

    protected TreeStructure buildTreeStructure(UIComponent component) {
        TreeStructure struct = new TreeStructure(
                component.getClass().getName(), component.getId());
        struct.setChildren(buildChildrenTreeStructure(component));
        struct.setFacets(buildFacetsTreeStructure(component));
        return struct;
    }

    protected TreeStructure[] buildChildrenTreeStructure(UIComponent component) {
        if (component.getChildCount() > 0) {
            List children = component.getChildren();
            List structs = new ArrayList();
            for (int i = 0; i < children.size(); ++i) {
                UIComponent child = (UIComponent) children.get(i);
                if (!child.isTransient()) {
                    TreeStructure childStruct = buildTreeStructure(child);
                    structs.add(childStruct);
                }
            }
            return (TreeStructure[]) structs.toArray(new TreeStructure[structs
                    .size()]);
        }
        return null;
    }

    protected Object[] buildFacetsTreeStructure(UIComponent component) {
        Map facets = component.getFacets();
        if (!facets.isEmpty()) {
            List structs = new ArrayList();
            for (Iterator it = facets.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                UIComponent child = (UIComponent) entry.getValue();
                if (!child.isTransient()) {
                    String facetName = (String) entry.getKey();
                    TreeStructure struct = buildTreeStructure(child);
                    structs.add(new Object[] { facetName, struct });
                }
            }
            return structs.toArray();
        }
        return null;
    }

    public void writeState(FacesContext context, SerializedView serializedView)
            throws IOException {

        UIViewRoot viewRoot = context.getViewRoot();
        RenderKit renderKit = getRenderKit(context, viewRoot.getRenderKitId());
        renderKit.getResponseStateManager().writeState(context, serializedView);
    }

    protected RenderKit getRenderKit(FacesContext context, String renderKitId) {
        return getRenderKitFactory().getRenderKit(context, renderKitId);
    }

    public UIViewRoot restoreView(FacesContext context, String viewId,
            String renderKitId) {

        ExternalContext extContext = context.getExternalContext();
        if (!isSavingStateInClient(context) && isViewModified(extContext, viewId)) {
            removeSerializedViewFromSession(extContext, viewId);
            removeLastModifiedFromSession(extContext, viewId);
            return null;
        }
        UIViewRoot viewRoot = restoreTreeStructure(context, viewId, renderKitId);
        if (viewRoot != null) {
            viewRoot.setViewId(viewId);
            restoreComponentState(context, viewRoot, renderKitId);
            /*
             * if (!isSavingStateInClient(context)) {
             * removeSerializedViewFromSession(context.getExternalContext(),
             * viewId); }
             */
            
//            if (getViewRootCacheSize(context) > 0) {
//                updateViewRootCache(context, viewId);
//            }
            
        }
        return viewRoot;
    }

    protected UIViewRoot restoreTreeStructure(FacesContext context,
            String viewId, String renderKitId) {

        if (isSavingStateInClient(context)) {
            return restoreTreeStructureFromClient(context, viewId, renderKitId);
        }
        return restoreTreeStructureFromServer(context, viewId);
    }

    protected UIViewRoot restoreTreeStructureFromClient(FacesContext context,
            String viewId, String renderKitId) {

        RenderKit renderKit = getRenderKit(context, renderKitId);
        ResponseStateManager responseStateManager = renderKit
                .getResponseStateManager();
        TreeStructure struct = (TreeStructure) responseStateManager
                .getTreeStructureToRestore(context, viewId);
        if (struct == null) {
            return null;
        }
        return (UIViewRoot) restoreTreeStructure(struct);
    }

    protected UIViewRoot restoreTreeStructureFromServer(FacesContext context,
            String viewId) {

        SerializedView serializedView = getSerializedViewFromSession(context
                .getExternalContext(), viewId);
        if (serializedView == null) {
            return null;
        }
        TreeStructure struct = (TreeStructure) serializedView.getStructure();
        return (UIViewRoot) restoreTreeStructure(struct);
    }

    protected UIComponent restoreTreeStructure(TreeStructure struct) {
        String className = struct.getComponentClassName();
        String id = struct.getComponentId();
        UIComponent component = (UIComponent) ClassUtil.newInstance(className);
        component.setId(id);
        restoreChildrenTreeStructure(component, struct.getChildren());
        restoreFacetsTreeStructure(component, struct.getFacets());
        return component;
    }

    protected void restoreChildrenTreeStructure(UIComponent component,
            TreeStructure[] structs) {
        if (structs != null) {
            for (int i = 0; i < structs.length; ++i) {
                UIComponent child = restoreTreeStructure(structs[i]);
                component.getChildren().add(child);
            }
        }
    }

    protected void restoreFacetsTreeStructure(UIComponent component,
            Object[] facets) {
        if (facets != null) {
            for (int i = 0, len = facets.length; i < len; i++) {
                Object[] array = (Object[]) facets[i];
                String facetName = (String) array[0];
                TreeStructure struct = (TreeStructure) array[1];
                UIComponent child = restoreTreeStructure(struct);
                component.getFacets().put(facetName, child);
            }
        }
    }

    protected void restoreComponentState(FacesContext context,
            UIViewRoot viewRoot, String renderKitId) {

        if (viewRoot.getRenderKitId() == null) {
            viewRoot.setRenderKitId(renderKitId);
        }
        if (isSavingStateInClient(context)) {
            restoreComponentStateFromClient(context, viewRoot, renderKitId);
        } else {
            restoreComponentStateFromServer(context, viewRoot);
        }
    }

    protected void restoreComponentStateFromClient(FacesContext context,
            UIViewRoot viewRoot, String renderKitId) {

        RenderKit renderKit = getRenderKit(context, renderKitId);
        ResponseStateManager responseStateManager = renderKit
                .getResponseStateManager();
        Object state = responseStateManager.getComponentStateToRestore(context);
        viewRoot.processRestoreState(context, state);
    }

    protected void restoreComponentStateFromServer(FacesContext context,
            UIViewRoot viewRoot) {

        SerializedView serializedView = getSerializedViewFromSession(context
                .getExternalContext(), viewRoot.getViewId());
        if (serializedView == null) {
            return;
        }
        Object state = serializedView.getState();
        if (state == null) {
            return;
        }
        viewRoot.processRestoreState(context, state);
    }

    protected RenderKitFactory getRenderKitFactory() {
        if (renderKitFactory == null) {
            renderKitFactory = (RenderKitFactory) FactoryFinder
                    .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        }
        return renderKitFactory;
    }

    protected ViewTemplateFactory getViewTemplateFactory() {
        if (viewTemplateFactory == null) {
            S2Container container = SingletonS2ContainerFactory.getContainer();
            viewTemplateFactory = (ViewTemplateFactory) container
                    .getComponent(ViewTemplateFactory.class);
        }
        return viewTemplateFactory;
    }

    protected void updateViewRootCache(FacesContext context, String viewId) {
        Map sessionMap = context.getExternalContext().getSessionMap();
        Map viewAccessInfoMap = null;
        if (!sessionMap.containsKey(VIEW_ACCESS_INFO)) {
            viewAccessInfoMap = new HashMap();
        } else {        
            viewAccessInfoMap = (Map)sessionMap.get(VIEW_ACCESS_INFO);
        }

        synchronized (viewAccessInfoMap) {
            Long currentTime = new Long(System.currentTimeMillis());
            viewAccessInfoMap.put(viewId, currentTime);
                       
            if (viewAccessInfoMap.size() > getViewRootCacheSize(context)) {
                String oldestAccessedViewId = getOldestAccessedViewId(viewAccessInfoMap);
                viewAccessInfoMap.remove(oldestAccessedViewId);
                
                ExternalContext extContext = context.getExternalContext();
                
                removeSerializedViewFromSession(extContext, oldestAccessedViewId);
                removeLastModifiedFromSession(extContext, oldestAccessedViewId);
                
                // recursive 
                updateViewRootCache(context, viewId);
            }
        }
        
        sessionMap.put(VIEW_ACCESS_INFO, viewAccessInfoMap);
    }
        
    protected String getOldestAccessedViewId(Map viewAccessInfoMap) {
        String oldestAccessedViewId = null;
        Long oldestAccessedTimestamp = new Long(System.currentTimeMillis());
        
        Set viewIdSet = viewAccessInfoMap.keySet();
        Iterator iterator = viewIdSet.iterator();
        while(iterator.hasNext()) {
            String viewId = (String)iterator.next();
            Long accessTimestamp = (Long)viewAccessInfoMap.get(viewId);
            if (oldestAccessedTimestamp.compareTo(accessTimestamp) > 0) {
                oldestAccessedTimestamp = accessTimestamp;
                oldestAccessedViewId = viewId;
            }
        }        
        return oldestAccessedViewId;              
    }

    protected int getViewRootCacheSize(FacesContext context) {
        if (viewRootCacheSize == 0) {
            ExternalContext extContext = context.getExternalContext();
            String initParameter = extContext.getInitParameter(UI_VIEW_ROOT_CACHE_SIZE);
          
            try {
                viewRootCacheSize = Integer.valueOf(initParameter).intValue();
            } catch (NumberFormatException exception) {
                viewRootCacheSize = -1;
            }
        }
        return viewRootCacheSize;
    }
        
}