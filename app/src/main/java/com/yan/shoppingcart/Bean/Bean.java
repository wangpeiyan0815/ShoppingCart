package com.yan.shoppingcart.Bean;

/**
 * Created by dell on 2017/2/19.
 */

public class Bean {
    private String name;
    private int price;
    private boolean isCheck_;
    private int count;
    public Bean(int price, String name,int count) {
        this.price = price;
        this.name = name;
        this.count = count;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isCheck_() {
        return isCheck_;
    }

    public void setCheck_(boolean check_) {
        isCheck_ = check_;
    }
}
