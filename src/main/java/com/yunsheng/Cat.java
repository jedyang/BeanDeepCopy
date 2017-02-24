package com.yunsheng;

import java.util.List;
import java.util.Map;

/**
 * Created by shengyun on 17/2/21.
 */
public class Cat {
    private String name;
    private Long length;

    private String kong;
    private Boolean boo;

    private List<CatHobby> hobby;

    private Map<String, String> myMap;


    public Map<String, String> getMyMap() {
        return myMap;
    }

    public void setMyMap(Map<String, String> myMap) {
        this.myMap = myMap;
    }

    public String getName() {
        return name;
    }

    public String getKong() {
        return kong;
    }

    public void setKong(String kong) {
        this.kong = kong;
    }

    public Boolean getBoo() {
        return boo;
    }

    public void setBoo(Boolean boo) {
        this.boo = boo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public List<CatHobby> getHobby() {
        return hobby;
    }

    public void setHobby(List<CatHobby> hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", kong='" + kong + '\'' +
                ", boo=" + boo +
                ", hobby=" + hobby +
                ", myMap=" + myMap +
                '}';
    }
}
