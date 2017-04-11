package cn.edu.cumt.entity;

public class Cart {
	private int shopping_num;
    private int suk;
    private int user_id;
    private String good_name;
    public Cart(){}

    public Cart(int shopping_num, int suk, int user_id, String good_name) {
        this.shopping_num = shopping_num;
        this.suk = suk;
        this.user_id = user_id;
        this.good_name = good_name;
    }

    public int getSuk() {
        return suk;
    }

    public void setSuk(int suk) {
        this.suk = suk;
    }

    public int getShopping_num() {
        return shopping_num;
    }

    public void setShopping_num(int shopping_num) {
        this.shopping_num = shopping_num;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }
}
