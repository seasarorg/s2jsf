package org.seasar.jsf.validator;

import java.util.List;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.render.RenderKitFactory;

import org.apache.myfaces.renderkit.html.HtmlRenderKitImpl;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.component.UIElement;
import org.seasar.jsf.component.html.S2HtmlInputSecret;
import org.seasar.jsf.component.html.S2HtmlInputText;
import org.seasar.jsf.component.html.S2HtmlInputTextarea;
import org.seasar.jsf.mock.MockFacesContext;

/**
 * @author cero-t
 * 
 */
public class S2AnywhereValidatorTest extends S2TestCase {
    public void testValidate() throws Exception {
        FactoryFinder.setFactory(FactoryFinder.RENDER_KIT_FACTORY,
                "org.apache.myfaces.renderkit.RenderKitFactoryImpl");
        RenderKitFactory factory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        HtmlRenderKitImpl renderKit = new HtmlRenderKitImpl();
        factory.addRenderKit("HTML_BASIC", renderKit);

        MockFacesContext context = new MockFacesContext();
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setRenderKitId("HTML_BASIC");
        context.setViewRoot(viewRoot);

        S2AnywhereValidator validator = new MockS2AnywhereValidator();
        UIComponent root = new UIElement();
        S2HtmlInputText component1 = new S2HtmlInputText();
        component1.setId("test1");
        component1.setValue("1");
        S2HtmlInputSecret component2 = new S2HtmlInputSecret();
        component2.setId("test2");
        component2.setValue("2");
        S2HtmlInputTextarea component3 = new S2HtmlInputTextarea();
        component3.setId("test3");
        component3.setValue("3");

        List children = root.getChildren();
        children.add(component1);
        children.add(component2);
        children.add(component3);

        validator.setTargetId("test1,test2,test3");
        validator.validate(context, root, "0");
    }
}
