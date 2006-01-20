package it00015;

public class BarActionImpl implements BarAction {
    
    private SomeBean someBean_;

    public String initialize() {
        someBean_.setValue("B");
        return null;
    }

    public SomeBean getSomeBean() {
        return someBean_;
    }

    public void setSomeBean(SomeBean someBean) {
        someBean_ = someBean;
    }

}
