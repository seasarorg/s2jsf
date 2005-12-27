package it00009;

import java.io.Serializable;

public class BooleanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean primitiveBoolean_;

    private Boolean boolean_;

    public Boolean getBoolean() {
        return boolean_;
    }

    public void setBoolean(Boolean b) {
        boolean_ = b;
    }

    public boolean isPrimitiveBoolean() {
        return primitiveBoolean_;
    }

    public void setPrimitiveBoolean(boolean primitiveBoolean) {
        primitiveBoolean_ = primitiveBoolean;
    }

}
