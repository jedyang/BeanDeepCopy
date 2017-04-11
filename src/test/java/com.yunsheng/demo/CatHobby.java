package com.yunsheng.demo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by shengyun on 17/2/21.
 */
public class CatHobby implements Serializable{
    private String what;

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
