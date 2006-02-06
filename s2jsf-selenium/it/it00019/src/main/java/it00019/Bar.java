package it00019;

import java.io.IOException;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class Bar extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "it00019.Bar";

    public static final String COMPONENT_TYPE = "it00019.Bar";

    private Object value_;

    private int time_;

    public void encodeBegin(FacesContext context) throws IOException {
    }

    public void encodeChildren(FacesContext context) throws IOException {
    }

    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("span", this);
        final String id = getId();
        if (id != null && 0 < id.length()) {
            writer.writeAttribute("id", id, null);
        }
        for (int i = getTime(); 0 < i; i--) {
            writer.writeText(value_, null);
        }
        writer.endElement("span");
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[3];
        values[0] = super.saveState(context);
        values[1] = value_;
        values[2] = new Integer(getTime());
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        setValue(values[1]);
        setTime(((Integer) values[2]).intValue());
    }

    public Object getValue() {
        return value_;
    }

    public void setValue(Object value) {
        value_ = value;
    }

    public int getTime() {
        return time_;
    }

    public void setTime(int time) {
        time_ = time;
    }

}
