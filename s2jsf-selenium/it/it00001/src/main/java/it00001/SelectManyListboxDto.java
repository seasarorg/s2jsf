package it00001;

import java.io.Serializable;

public class SelectManyListboxDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String[] a_;

    private String[] b_;

    public String[] getA() {
        return a_;
    }

    public void setA(String[] a) {
        a_ = a;
    }

    public String[] getB() {
        return b_;
    }

    public void setB(String[] b) {
        b_ = b;
    }

}
