package it00014;

public class SomeActionImpl implements SomeAction {

    public String initialize() {
        System.out.println("initializeが実行されました: " + getClass().getName());
        return null;
    }

}
