package it00017;

public class FooActionImpl implements FooAction {

    private FooDto fooDto_;

    public String initialize() {
        System.out.println("FooActionImpl.initialize()");
        fooDto_.setFooValue(fooDto_.getFooValue() + 1);
        return "second";
    }

    public FooDto getFooDto() {
        return fooDto_;
    }

    public void setFooDto(FooDto fooDto) {
        fooDto_ = fooDto;
    }

}
