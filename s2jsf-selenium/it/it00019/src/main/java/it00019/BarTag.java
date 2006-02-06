package it00019;

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

public class BarTag extends UIComponentTag {

    private String value_;

    private String time_;

    public String getComponentType() {
        return Bar.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        Bar bar = (Bar) component;
        final String value = getValue();
        if (isValueReference(value)) {
            bar.setValueBinding("value", createValueBinding(value));
        } else {
            bar.setValue(value);
        }
        final String time = getTime();

        if (isValueReference(value)) {
            bar.setValueBinding("time", createValueBinding(time));
        } else {
            if (time != null) {
                try {
                    bar.setTime(Integer.parseInt(time));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ValueBinding createValueBinding(final String value) {
        return getFacesContext().getApplication().createValueBinding(value);
    }

    public void release() {
        super.release();
        value_ = null;
    }

    public String getValue() {
        return value_;
    }

    public void setValue(String value) {
        value_ = value;
    }

    public String getTime() {
        return time_;
    }

    public void setTime(String time) {
        time_ = time;
    }

}
