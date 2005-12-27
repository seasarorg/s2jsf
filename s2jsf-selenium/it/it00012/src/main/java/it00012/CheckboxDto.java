package it00012;

import java.io.Serializable;

public class CheckboxDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean a_;
    private boolean b_;
    private boolean c_;
    private boolean requested_A_;
    private boolean requested_B_;
    private boolean requested_C_;
    private boolean dto_A_;
    private boolean dto_B_;
    private boolean dto_C_;

    public boolean isA() {
        return a_;
    }

    public void setA(boolean a) {
        a_ = a;
    }

    public boolean isB() {
        return b_;
    }

    public void setB(boolean b) {
        b_ = b;
    }

    public boolean isC() {
        return c_;
    }

    public void setC(boolean c) {
        c_ = c;
    }

    public boolean isRequested_A() {
        return requested_A_;
    }

    public void setRequested_A(boolean d) {
        requested_A_ = d;
    }

    public boolean isRequested_B() {
        return requested_B_;
    }

    public void setRequested_B(boolean e) {
        requested_B_ = e;
    }

    public boolean isRequested_C() {
        return requested_C_;
    }

    public void setRequested_C(boolean f) {
        requested_C_ = f;
    }

    public boolean isDto_A() {
        return dto_A_;
    }

    public void setDto_A(boolean dto_A) {
        dto_A_ = dto_A;
    }

    public boolean isDto_B() {
        return dto_B_;
    }

    public void setDto_B(boolean dto_B) {
        dto_B_ = dto_B;
    }

    public boolean isDto_C() {
        return dto_C_;
    }

    public void setDto_C(boolean dto_C) {
        dto_C_ = dto_C;
    }

}
