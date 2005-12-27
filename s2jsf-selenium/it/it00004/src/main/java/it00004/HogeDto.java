package it00004;

public class HogeDto {

    private String hoge;

    public HogeDto() {
    }

    public HogeDto(String hoge) {
        this.hoge = hoge;
    }

    public String getHoge() {
        return hoge;
    }

    public void setHoge(String hoge) {
        System.out.println("setHoge=" + hoge);
        this.hoge = hoge;
    }

}
