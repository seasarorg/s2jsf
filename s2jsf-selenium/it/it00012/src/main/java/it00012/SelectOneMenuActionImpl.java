package it00012;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class SelectOneMenuActionImpl implements SelectOneMenuAction {

    private SelectOneMenuDto selectOneMenuDto_;

    private HttpServletRequest request_;

    public String initialize() {
        selectOneMenuDto_.setA("1");
        selectOneMenuDto_.setB("2");
        selectOneMenuDto_.setC("3");
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

        selectOneMenuDto_.setRequested_A(request_.getParameter("form:a"));
        selectOneMenuDto_.setRequested_B(request_.getParameter("form:b"));
        selectOneMenuDto_.setRequested_C(request_.getParameter("form:c"));

        selectOneMenuDto_.setDto_A(selectOneMenuDto_.getA());
        selectOneMenuDto_.setDto_B(selectOneMenuDto_.getB());
        selectOneMenuDto_.setDto_C(selectOneMenuDto_.getC());
        return null;
    }

    public void setSelectOneMenuDto(SelectOneMenuDto selectOneMenuDto) {
        selectOneMenuDto_ = selectOneMenuDto;
    }

}
