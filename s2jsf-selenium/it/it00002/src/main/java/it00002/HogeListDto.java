package it00002;

import java.util.LinkedList;
import java.util.List;

public class HogeListDto {

    private List hogeList_;

    public void setInit(String dummy) {
        System.out.println("HogeListDto.setInit() is called.");
        LinkedList lla = new LinkedList();
        lla.add(new HogeDto("a1"));
        lla.add(new HogeDto("a2"));
        lla.add(new HogeDto("a3"));
        LinkedList llb = new LinkedList();
        llb.add(new HogeDto("b1"));
        llb.add(new HogeDto("b2"));
        // llb.add(new HogeDto("b3")); ここのコメントを外すと例外は発生しない
        LinkedList ll = new LinkedList();
        ll.add(lla);
        ll.add(llb);
        hogeList_ = ll;
    }

    public List getHogeList() {
        return hogeList_;
    }

    public void setHogeList(List hogeList) {
        System.out.println("HogeListDto.setHogeList() is called.");
        this.hogeList_ = hogeList;
    }

}
