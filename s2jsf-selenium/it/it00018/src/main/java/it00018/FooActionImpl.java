package it00018;

public class FooActionImpl implements FooAction {

    private String textByValueBinding_;

    private String textByRequest_;

    private String prevTextByRequest_;

    public FooActionImpl() {
        // System.out.println("FooActionImpl()");
    }

    public String doSomething() {
        // System.out.println("FooActionImpl.doSomething()");
        // final HttpServletRequest request = SingletonS2ContainerFactory
        // .getContainer().getRequest();
        // for (Enumeration names = request.getAttributeNames(); names
        // .hasMoreElements();) {
        // Object name = names.nextElement();
        // Object value = request.getAttribute(name.toString());
        // System.out.println("[attr]" + name + "=" + value);
        // }
        // for (Enumeration names = request.getParameterNames(); names
        // .hasMoreElements();) {
        // Object name = names.nextElement();
        // String value = request.getParameter(name.toString());
        // System.out.println("[param]" + name + "=" + value);
        // }
        return null;
    }

    public String getTextByValueBinding() {
        return textByValueBinding_;
    }

    // 画面で""を入力すると、ValueBindingによってupdateModelValues時に
    // nullがセットされる。
    public void setTextByValueBinding(String text) {
        // System.out.println("FooActionImpl.setText() " + text + " <- "
        // + String.valueOf(text_) + " ("
        // + (text == null ? "real null" : text.getClass().getName()) + ")");
        // if (text == null) {
        // throw new RuntimeException("なぜかnull");
        // }
        textByValueBinding_ = text;
    }

    public String getTextByRequest() {
        return textByRequest_;
    }

    public void setTextByRequest(String textByRequest) {
        // System.out.println("FooActionImpl.setTextByRequest() "
        // + String.valueOf(textByRequest_)
        // + " ("
        // + (textByRequest == null ? "real null" : textByRequest
        // .getClass().getName()) + ")" + " -> " + textByRequest);
        prevTextByRequest_ = textByRequest_;
        textByRequest_ = textByRequest;
    }

    public String getPrevTextByRequest() {
        return prevTextByRequest_;
    }

    public void setPrevTextByRequest(String prevTextByRequest) {
        // System.out.println("FooActionImpl.setPrevTextByRequest() "
        // + prevTextByRequest_ + " -> " + prevTextByRequest);
        prevTextByRequest_ = prevTextByRequest;
    }

}
