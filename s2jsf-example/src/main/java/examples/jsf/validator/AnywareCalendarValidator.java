package examples.jsf.validator;

import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.seasar.jsf.util.MessageUtil;
import org.seasar.jsf.util.UIComponentUtil;
import org.seasar.jsf.validator.S2AnywhereValidator;

public class AnywareCalendarValidator extends S2AnywhereValidator {
    public static final String ARGS_MESSAGE_ID = "examples.jsf.validator.CalendarValidator.ARGS";

    public static final String LENIENT_MESSAGE_ID = "examples.jsf.validator.CalendarValidator.LENIENT";

    protected void doValidate(FacesContext context, UIComponent component,
            Object value, UIComponent[] targetComponents, Object[] targetValues)
            throws ValidatorException {

        if (targetValues == null) {
            return;
        }

        if (targetValues.length < 3 || targetValues[0] == null
                || targetValues[1] == null || targetValues[2] == null) {
            Object[] args = { UIComponentUtil.getLabel(component) };
            throw new ValidatorException(MessageUtil.getErrorMessage(
                    ARGS_MESSAGE_ID, args));
        }

        int year;
        int month;
        int date;
        try {
            year = Integer.parseInt(targetValues[0].toString());
            month = Integer.parseInt(targetValues[1].toString()) - 1;
            date = Integer.parseInt(targetValues[2].toString());
        } catch (NumberFormatException e) {
            Object[] args = { UIComponentUtil.getLabel(component) };
            throw new ValidatorException(MessageUtil.getErrorMessage(
                    ARGS_MESSAGE_ID, args));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);

        try {
            calendar.getTime();
        } catch (IllegalArgumentException ex) {
            Object[] args = { UIComponentUtil.getLabel(component),
                    targetValues[0], targetValues[1], targetValues[2] };
            throw new ValidatorException(MessageUtil.getErrorMessage(
                    LENIENT_MESSAGE_ID, args));
        }
    }
}
