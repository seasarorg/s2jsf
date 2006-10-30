package org.seasar.jsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class MockS2MultiFieldValidator extends S2MultiFieldValidator {

    protected void doValidate(FacesContext context, UIComponent component,
            Object value, UIComponent[] targetComponent, Object[] targetValues)
            throws ValidatorException {
        int sum = 0;
        for (int i = 0; i < targetValues.length; i++) {
            sum += Integer.parseInt(targetValues[i].toString());
        }
        sum += Integer.parseInt(value.toString());

        if (sum != 6) {
            throw new ValidatorException(new FacesMessage("sum is not 6."));
        }
    }

}
