package it00012;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class SelectManyListboxActionImpl implements SelectManyListboxAction {

    private SelectManyListboxDto selectManyListboxDto_;

    private HttpServletRequest request_;

    public void setRequest(HttpServletRequest request) {
        request_ = request;
    }

    public String initialize() {
        selectManyListboxDto_.setA(new String[] { "1", "2" });
        selectManyListboxDto_.setB(new String[] { "2", "3" });
        selectManyListboxDto_.setC(new String[] { "1", "3" });
        return null;
    }

    public String doSomething() {
        for (Enumeration enu = request_.getParameterNames(); enu
            .hasMoreElements();) {
            String key = (String) enu.nextElement();
            String[] values = request_.getParameterValues(key);
            System.out.print("[request param]" + key + "=");
            for (int i = 0; i < values.length; i++) {
                if (0 < i) {
                    System.out.print(", ");
                }
                System.out.print(values[i]);
            }
            System.out.println();
        }

        selectManyListboxDto_.setRequested_A(request_
            .getParameterValues("form:a"));
        selectManyListboxDto_.setRequested_B(request_
            .getParameterValues("form:b"));
        selectManyListboxDto_.setRequested_C(request_
            .getParameterValues("form:c"));

        System.out.println("selectManyListboxDto_.getA()="
            + selectManyListboxDto_.getA());
        System.out.println("selectManyListboxDto_.getB()="
            + selectManyListboxDto_.getB());

        selectManyListboxDto_.setDto_A(selectManyListboxDto_.getA());
        selectManyListboxDto_.setDto_B(selectManyListboxDto_.getB());
        selectManyListboxDto_.setDto_C(selectManyListboxDto_.getC());
        return null;
    }

    public void setSelectManyListboxDto(
        SelectManyListboxDto selectManyListboxDto) {
        selectManyListboxDto_ = selectManyListboxDto;
    }

}
