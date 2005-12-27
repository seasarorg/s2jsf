package it00012;

import java.io.Serializable;

public class InputTextDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String a_;
    private String b_;
    private String c_;
    private String requested_A_;
    private String requested_B_;
    private String requested_C_;
    private String dto_A_;
    private String dto_B_;
    private String dto_C_;

    public String getA() {
        return a_;
    }

    public void setA(String a) {
        a_ = a;
    }

    public String getB() {
        return b_;
    }

    public void setB(String b) {
        b_ = b;
    }

    public String getC() {
        return c_;
    }

    public void setC(String c) {
        c_ = c;
    }

    public String getRequested_A() {
        return requested_A_;
    }

    public void setRequested_A(String d) {
        requested_A_ = d;
    }

    public String getRequested_B() {
        return requested_B_;
    }

    public void setRequested_B(String e) {
        requested_B_ = e;
    }

    public String getRequested_C() {
        return requested_C_;
    }

    public void setRequested_C(String f) {
        requested_C_ = f;
    }

    public String toString() {
        return "a=" + a_ + ", b=" + b_ + ", c=" + c_;
    }

    public String getDto_A() {
        return dto_A_;
    }

    public void setDto_A(String dto_A) {
        dto_A_ = dto_A;
    }

    public String getDto_B() {
        return dto_B_;
    }

    public void setDto_B(String dto_B) {
        dto_B_ = dto_B;
    }

    public String getDto_C() {
        return dto_C_;
    }

    public void setDto_C(String dto_C) {
        dto_C_ = dto_C;
    }

}
