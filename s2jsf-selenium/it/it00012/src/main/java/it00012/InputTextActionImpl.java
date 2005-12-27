package it00012;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class InputTextActionImpl implements InputTextAction {

    private InputTextDto inputTextDto_;

    private HttpServletRequest request_;

    public String initialize() {
        inputTextDto_.setA("a1");
        inputTextDto_.setB("b1");
        inputTextDto_.setC("c1");
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

        inputTextDto_.setRequested_A(request_.getParameter("form:a"));
        inputTextDto_.setRequested_B(request_.getParameter("form:b"));
        inputTextDto_.setRequested_C(request_.getParameter("form:c"));

        inputTextDto_.setDto_A(inputTextDto_.getA());
        inputTextDto_.setDto_B(inputTextDto_.getB());
        inputTextDto_.setDto_C(inputTextDto_.getC());
        return null;
    }

    public void setInputTextDto(InputTextDto fooDto) {
        inputTextDto_ = fooDto;
    }

}
