package com.yunsheng;

import java.util.List;
import java.util.Map;

/**
 * Created by shengyun on 17/2/21.
 */
public class Dog {
    private String name;
    private Long length;

    private List<DogHobby> hobby;

    private String kong;
    private Boolean boo;

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

    public void setName(String name) {
        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public List<DogHobby> getHobby() {
        return hobby;
    }

    public void setHobby(List<DogHobby> hobby) {
        this.hobby = hobby;
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

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", hobby=" + hobby +
                ", kong='" + kong + '\'' +
                ", boo=" + boo +
                ", myMap=" + myMap +
                '}';
    }
}
