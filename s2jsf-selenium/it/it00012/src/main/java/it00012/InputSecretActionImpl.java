package it00012;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class InputSecretActionImpl implements InputSecretAction {

    private InputSecretDto inputSecretDto_;

    private HttpServletRequest request_;

    public String initialize() {
        System.out.println("InputSecretActionImpl.initialize()");
        inputSecretDto_.setA("a1");
        inputSecretDto_.setB("b1");
        inputSecretDto_.setC("c1");
        inputSecretDto_.setD("d1");
        inputSecretDto_.setE("e1");
        inputSecretDto_.setF("f1");
        return null;
    }

    public void setRequest(HttpServletRequest request) {
        request_ = request;
    }

    public String doSomething() {
        System.out.println("InputSecretActionImpl.doSomething()");
        for (Enumeration enu = request_.getParameterNames(); enu
            .hasMoreElements();) {
            String key = (String) enu.nextElement();
            String value = request_.getParameter(key);
            System.out.println("[request param]" + key + "=" + value);
        }

        inputSecretDto_.setRequested_A(request_.getParameter("form:a"));
        inputSecretDto_.setRequested_B(request_.getParameter("form:b"));
        inputSecretDto_.setRequested_C(request_.getParameter("form:c"));
        inputSecretDto_.setRequested_D(request_.getParameter("form:d"));
        inputSecretDto_.setRequested_E(request_.getParameter("form:e"));
        inputSecretDto_.setRequested_F(request_.getParameter("form:f"));

        inputSecretDto_.setDto_A(inputSecretDto_.getA());
        inputSecretDto_.setDto_B(inputSecretDto_.getB());
        inputSecretDto_.setDto_C(inputSecretDto_.getC());
        inputSecretDto_.setDto_D(inputSecretDto_.getD());
        inputSecretDto_.setDto_E(inputSecretDto_.getE());
        inputSecretDto_.setDto_F(inputSecretDto_.getF());

        return null;
    }

    public void setInputSecretDto(InputSecretDto inputSecretDto) {
        inputSecretDto_ = inputSecretDto;
    }

}
