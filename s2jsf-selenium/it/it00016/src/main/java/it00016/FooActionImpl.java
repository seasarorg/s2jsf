package it00016;

public class FooActionImpl implements FooAction {
    
    public String initialize() {
        throw new SomeException("for [it00016]");
    }

}
