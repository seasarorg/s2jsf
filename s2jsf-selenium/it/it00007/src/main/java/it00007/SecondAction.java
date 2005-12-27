package it00007;

public class SecondAction {

    public SecondAction() {
        System.out.println("SecondAction.SecondAction()");
    }

    public String initialize() {
        throw new RuntimeException("second error");
    }

}
