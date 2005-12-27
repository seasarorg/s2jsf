package it00010;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HogeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public List getItems() {
        List list = new ArrayList();
        list.add(new HogeItem("Item1000"));
        list.add(new HogeItem("Item1001"));
        list.add(new HogeItem("Item1002"));
        list.add(new HogeItem("Item1003"));
        return list;
    }

    public static class HogeItem implements Serializable {

        public HogeItem(String value) {
            value_ = value;
        }

        private static final long serialVersionUID = 1L;

        private String value_;

        public String getValue() {
            return value_;
        }

        public void setValue(String value) {
            value_ = value;
        }
    }

}
