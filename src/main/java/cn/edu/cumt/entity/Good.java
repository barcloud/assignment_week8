package cn.edu.cumt.entity;

/**
 * Created by fc751 on 2017/3/23.
 */
public class Good {
    private int suk;
    private String good_name;
    public Good() {}
    public Good(int suk, String good_name) {
        this.suk = suk;
        this.good_name = good_name;
    }

    public int getSuk() {
        return suk;
    }

    public void setSuk(int suk) {
        this.suk = suk;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }
}
