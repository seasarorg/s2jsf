package it00007;

public class FirstAction {

    public FirstAction() {
        System.out.println("FirstAction.FirstAction()");
    }

    public String initialize() {
        throw new RuntimeException("first error");
    }

}
