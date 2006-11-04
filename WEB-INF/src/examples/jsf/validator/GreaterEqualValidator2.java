package examples.jsf.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.seasar.jsf.util.MessageUtil;
import org.seasar.jsf.util.UIComponentUtil;
import org.seasar.jsf.validator.S2AnywhereValidator;
import org.seasar.jsf.validator.S2GreaterEqualValidator;

/**
 * @author yone
 */
public class GreaterEqualValidator2 extends S2AnywhereValidator {

    protected void doValidate(FacesContext context, UIComponent component,
            Object value, UIComponent[] targetComponents, Object[] targetValues)
            throws ValidatorException {
        if (targetValues == null) {
            return;
        }
        if (!(value instanceof Comparable)
                || ((Comparable) value).compareTo(targetValues[0]) < 0) {

            Object[] args = { UIComponentUtil.getLabel(component),
                    UIComponentUtil.getLabel(targetComponents[0]) };
            throw new ValidatorException(MessageUtil.getS2ErrorMessage(
                    S2GreaterEqualValidator.GE_MESSAGE_ID, args));
        }
    }

}
