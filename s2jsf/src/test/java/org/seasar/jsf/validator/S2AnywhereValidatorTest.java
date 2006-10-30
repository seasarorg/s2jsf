package org.seasar.jsf.validator;

import java.util.List;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.seasar.jsf.component.UIElement;
import org.seasar.jsf.component.html.S2HtmlInputText;
import org.seasar.teeda.core.application.ComponentLookupStrategy;
import org.seasar.teeda.core.application.impl.DefaultComponentLookupStrategy;
import org.seasar.teeda.core.render.html.HtmlInputTextRenderer;
import org.seasar.teeda.core.render.html.HtmlRenderKitImpl;
import org.seasar.teeda.core.render.html.HtmlRenderKitImpl.RendererHolder;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author cero-t
 * 
 */
public class S2AnywhereValidatorTest extends TeedaTestCase {
    public void testValidate() throws Exception {
        FactoryFinder.setFactory(FactoryFinder.RENDER_KIT_FACTORY,
                "org.seasar.teeda.core.render.RenderKitFactoryImpl");
        RenderKitFactory factory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        HtmlRenderKitImpl renderKit = new HtmlRenderKitImpl();
        ComponentLookupStrategy strategy = new DefaultComponentLookupStrategy();
        Renderer renderer = new HtmlInputTextRenderer();
        RendererHolder holder = new RendererHolder();
        holder.setRenderer(renderer);
        register(holder,
                "javax.faces.Input.javax.faces.Text_RENDERER_FACES_CONFIG");
        renderKit.setComponentLookupStrategy(strategy);
        factory.addRenderKit("HTML_BASIC", renderKit);

        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setRenderKitId("HTML_BASIC");
        getFacesContext().setViewRoot(viewRoot);

        S2AnywhereValidator validator = new MockS2AnywhereValidator();
        UIComponent root = new UIElement();
        S2HtmlInputText component1 = new S2HtmlInputText();
        component1.setId("test1");
        component1.setSubmittedValue("1");
        S2HtmlInputText component2 = new S2HtmlInputText();
        component2.setId("test2");
        component2.setSubmittedValue("2");
        S2HtmlInputText component3 = new S2HtmlInputText();
        component3.setId("test3");
        component3.setSubmittedValue("3");

        List children = root.getChildren();
        children.add(component1);
        children.add(component2);
        children.add(component3);

        validator.setTargetId("test1,test2,test3");
        validator.validate(getFacesContext(), root, "0");
    }
}
