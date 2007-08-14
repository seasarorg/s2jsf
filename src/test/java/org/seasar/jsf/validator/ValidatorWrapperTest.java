package org.seasar.jsf.validator;

import java.util.Locale;

import javax.faces.component.UIParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.validator.ValidatorException;

import junit.framework.TestCase;

import org.seasar.jsf.component.html.S2HtmlInputText;
import org.seasar.jsf.mock.MockApplication;
import org.seasar.jsf.mock.MockFacesContext;

/**
 * @author cero-t
 * 
 */
public class ValidatorWrapperTest extends TestCase {

    private S2LengthValidator lengthValidator;

    private ValidatorWrapper validatorWrapper;

    private MockFacesContext context;

    protected void setUp() throws Exception {
        lengthValidator = new S2LengthValidator();
        UIParameter param = new UIParameter();
        param.setName("maximum");
        param.setValue(new Integer(5));
        validatorWrapper = new ValidatorWrapper(lengthValidator,
                new UIParameter[] { param });
        context = new MockFacesContext();
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setLocale(Locale.getDefault());
        MockApplication application = new MockApplication();
        context.setApplication(application);
    }

    public void testValidate() throws Exception {
        S2HtmlInputText component = new S2HtmlInputText();
        component.setId("hoge");
        validatorWrapper.validate(context, component, "1");
        assertEquals(5, lengthValidator.getMaximum());
        try {
            validatorWrapper.validate(context, component, "100000");
            fail();
        } catch (ValidatorException e) {
            System.out.println(e);
        }
    }

    public void testStateHolder() throws Exception {
        Object state = validatorWrapper.saveState(context);
        ValidatorWrapper vw = new ValidatorWrapper();
        vw.restoreState(context, state);
        UIParameter[] myParams = vw.getParams();
        assertEquals(1, myParams.length);
        UIParameter param = myParams[0];
        assertEquals("maximum", param.getName());
        assertEquals(new Integer(5), param.getValue());
    }
}
