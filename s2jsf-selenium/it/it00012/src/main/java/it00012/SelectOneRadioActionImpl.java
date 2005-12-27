package it00012;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class SelectOneRadioActionImpl implements SelectOneRadioAction {

    private SelectOneRadioDto selectOneRadioDto_;

    private HttpServletRequest request_;

    public String initialize() {
        selectOneRadioDto_.setA("1");
        selectOneRadioDto_.setB("2");
        selectOneRadioDto_.setC("3");
        return null;
    }

    public void setRequest(HttpServletRequest request) {
        request_ = request;
    }

    public String doSomething() {
        for (Enumeration enu = request_.getParameterNames(); enu
            .hasMoreElements();) {
            String key = (String) enu.nextElement();
            String value = request_.getParameter(key);
            System.out.println("[request param]" + key + "=" + value);
        }

        selectOneRadioDto_.setRequested_A(request_.getParameter("form:a"));
        selectOneRadioDto_.setRequested_B(request_.getParameter("form:b"));
        selectOneRadioDto_.setRequested_C(request_.getParameter("form:c"));

        selectOneRadioDto_.setDto_A(selectOneRadioDto_.getA());
        selectOneRadioDto_.setDto_B(selectOneRadioDto_.getB());
        selectOneRadioDto_.setDto_C(selectOneRadioDto_.getC());
        return null;
    }

    public void setSelectOneRadioDto(SelectOneRadioDto selectOneRadioDto) {
        selectOneRadioDto_ = selectOneRadioDto;
    }

}
