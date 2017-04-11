package com.yunsheng.demo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by shengyun on 17/2/21.
 */
public class Dog {
    private String name;
    private Long length;

    private String kong;
    private Boolean boo;

    private double dogOnly;

    // 对象集合
    private List<DogHobby> complexList;
    private Set<DogHobby> complexSet;
    private Map<String, DogHobby> complexMap;

    // 基本类型的集合
    private List<String> myList;
    private Map<String, String> myMap;
    private Set<String> mySet;

    public double getDogOnly() {
        return dogOnly;
    }

    public void setDogOnly(double dogOnly) {
        this.dogOnly = dogOnly;
    }

    public List<DogHobby> getComplexList() {
        return complexList;
    }

    public void setComplexList(List<DogHobby> complexList) {
        this.complexList = complexList;
    }

    public Set<DogHobby> getComplexSet() {
        return complexSet;
    }

    public void setComplexSet(Set<DogHobby> complexSet) {
        this.complexSet = complexSet;
    }

    public Map<String, DogHobby> getComplexMap() {
        return complexMap;
    }

    public void setComplexMap(Map<String, DogHobby> complexMap) {
        this.complexMap = complexMap;
    }

    public List<String> getMyList() {
        return myList;
    }

    public void setMyList(List<String> myList) {
        this.myList = myList;
    }

    public Set<String> getMySet() {
        return mySet;
    }

    public void setMySet(Set<String> mySet) {
        this.mySet = mySet;
    }

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
        return ToStringBuilder.reflectionToString(this);
    }
}
