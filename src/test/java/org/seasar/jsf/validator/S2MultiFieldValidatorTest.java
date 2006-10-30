package org.seasar.jsf.validator;

import java.util.List;

import javax.faces.component.UIComponent;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.component.UIElement;
import org.seasar.jsf.component.html.S2HtmlInputSecret;
import org.seasar.jsf.component.html.S2HtmlInputText;
import org.seasar.jsf.component.html.S2HtmlInputTextarea;

/**
 * @author cero-t
 * 
 */
public class S2MultiFieldValidatorTest extends S2TestCase {
    public void testValidate1() throws Exception {
        S2MultiFieldValidator validator = new MockS2MultiFieldValidator();
        UIComponent root = new UIElement();
        S2HtmlInputText component1 = new S2HtmlInputText();
        component1.setId("test1");
        component1.setValue("1");
        S2HtmlInputSecret component2 = new S2HtmlInputSecret();
        component2.setId("test2");
        component2.setValue("2");
        S2HtmlInputTextarea component3 = new S2HtmlInputTextarea();
        component3.setId("test3");

        List children = root.getChildren();
        children.add(component1);
        children.add(component2);
        children.add(component3);

        validator.setTargetId("test1,test2");
        validator.validate(null, component3, "3");
    }

    public void testValidate2() throws Exception {
        S2MultiFieldValidator validator = new MockS2MultiFieldValidator();
        UIComponent root = new UIElement();
        S2HtmlInputText component1 = new S2HtmlInputText();
        component1.setId("test1");
        component1.setSubmittedValue("1");
        S2HtmlInputSecret component2 = new S2HtmlInputSecret();
        component2.setId("test2");
        component2.setSubmittedValue("1");
        S2HtmlInputTextarea component3 = new S2HtmlInputTextarea();
        component3.setId("test3");

        List children = root.getChildren();
        children.add(component1);
        children.add(component2);
        children.add(component3);

        validator.setTargetId("test1,test2");
        try {
            validator.validate(null, component3, "3");
            fail();
        } catch (RuntimeException e) {
            // success
        }
    }
}
