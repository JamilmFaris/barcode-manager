package com.example.inventorydronedesign.Model;

public class Bookmark {
    private Integer type;

    public Bookmark(Integer type) {
        this.type = type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        if(type==null)
            return 1;
        return type;
    }
}
