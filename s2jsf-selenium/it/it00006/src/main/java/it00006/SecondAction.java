package it00006;

import java.util.ArrayList;
import java.util.List;

public class SecondAction {

    private List hogeList = new ArrayList();

    public SecondAction() {
        System.out.println("HogeAction.HogeAction()");
    }

    public String initialize() {
        hogeList.add(new HogeDto("hoge1"));
        hogeList.add(new HogeDto("hoge2"));
        hogeList.add(new HogeDto("hoge3"));
        return null;
    }

    public List getHogeList() {
        return hogeList;
    }

}
