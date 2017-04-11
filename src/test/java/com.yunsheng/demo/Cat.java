package com.yunsheng.demo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by shengyun on 17/2/21.
 */
public class Cat implements Serializable{

    private String name;
    private Long length;

    private String kong;
    private Boolean boo;

    // 对象集合
    private List<CatHobby> complexList;
    private Set<CatHobby> complexSet;
    private Map<String, CatHobby> complexMap;

    // 基本类型的集合
    private List<String> myList;
    private Map<String, String> myMap;
    private Set<String> mySet;

    private int catOnly;

    public int getCatOnly() {
        return catOnly;
    }

    public void setCatOnly(int catOnly) {
        this.catOnly = catOnly;
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

    public List<CatHobby> getComplexList() {
        return complexList;
    }

    public void setComplexList(List<CatHobby> complexList) {
        this.complexList = complexList;
    }

    public Set<CatHobby> getComplexSet() {
        return complexSet;
    }

    public void setComplexSet(Set<CatHobby> complexSet) {
        this.complexSet = complexSet;
    }

    public Map<String, CatHobby> getComplexMap() {
        return complexMap;
    }

    public void setComplexMap(Map<String, CatHobby> complexMap) {
        this.complexMap = complexMap;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
