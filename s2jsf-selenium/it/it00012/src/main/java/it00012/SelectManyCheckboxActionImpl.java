package it00012;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class SelectManyCheckboxActionImpl implements SelectManyCheckboxAction {

    private SelectManyCheckboxDto selectManyCheckboxDto_;

    private HttpServletRequest request_;

    public void setRequest(HttpServletRequest request) {
        request_ = request;
    }

    public String initialize() {
        selectManyCheckboxDto_.setA(new String[] { "1", "2", "3" });
        selectManyCheckboxDto_.setB(new String[] { "1", "2", "3" });
        selectManyCheckboxDto_.setC(new String[] { "1", "2", "3" });
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

        selectManyCheckboxDto_.setRequested_A(request_
            .getParameterValues("form:a"));
        selectManyCheckboxDto_.setRequested_B(request_
            .getParameterValues("form:b"));
        selectManyCheckboxDto_.setRequested_C(request_
            .getParameterValues("form:c"));

        selectManyCheckboxDto_.setDto_A(selectManyCheckboxDto_.getA());
        selectManyCheckboxDto_.setDto_B(selectManyCheckboxDto_.getB());
        selectManyCheckboxDto_.setDto_C(selectManyCheckboxDto_.getC());
        return null;
    }

    public void setSelectManyCheckboxDto(
        SelectManyCheckboxDto selectManyCheckboxDto) {
        selectManyCheckboxDto_ = selectManyCheckboxDto;
    }

}
