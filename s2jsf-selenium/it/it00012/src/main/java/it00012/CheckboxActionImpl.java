package it00012;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class CheckboxActionImpl implements CheckboxAction {

    private CheckboxDto checkboxDto_;

    private HttpServletRequest request_;

    public void setRequest(HttpServletRequest request) {
        request_ = request;
    }

    public String initialize() {
        checkboxDto_.setA(true);
        checkboxDto_.setB(true);
        checkboxDto_.setC(true);
        return null;
    }

    public String doSomething() {
        for (Enumeration enu = request_.getParameterNames(); enu
            .hasMoreElements();) {
            String key = (String) enu.nextElement();
            String value = request_.getParameter(key);
            System.out.println("[request param]" + key + "=" + value);
        }

        checkboxDto_.setRequested_A(toBoolean(request_.getParameter("form:a")));
        checkboxDto_.setRequested_B(toBoolean(request_.getParameter("form:b")));
        checkboxDto_.setRequested_C(toBoolean(request_.getParameter("form:c")));

        checkboxDto_.setDto_A(checkboxDto_.isA());
        checkboxDto_.setDto_B(checkboxDto_.isB());
        checkboxDto_.setDto_C(checkboxDto_.isC());

        return null;
    }

    private boolean toBoolean(String s) {
        return "on".equalsIgnoreCase(s) || "true".equalsIgnoreCase(s);
    }

    public void setCheckboxDto(CheckboxDto checkboxDto) {
        checkboxDto_ = checkboxDto;
    }

}
