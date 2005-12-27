package it00012;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class TextareaActionImpl implements TextareaAction {

    private TextareaDto textareaDto_;

    private HttpServletRequest request_;

    public String initialize() {
        textareaDto_.setA("a1");
        textareaDto_.setB("b1");
        textareaDto_.setC("c1");
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

        textareaDto_.setRequested_A(request_.getParameter("form:a"));
        textareaDto_.setRequested_B(request_.getParameter("form:b"));
        textareaDto_.setRequested_C(request_.getParameter("form:c"));

        textareaDto_.setDto_A(textareaDto_.getA());
        textareaDto_.setDto_B(textareaDto_.getB());
        textareaDto_.setDto_C(textareaDto_.getC());
        return null;
    }

    public void setTextareaDto(TextareaDto textareaDto) {
        textareaDto_ = textareaDto;
    }

}
