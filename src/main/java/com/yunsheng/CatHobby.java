package com.yunsheng;

/**
 * Created by shengyun on 17/2/21.
 */
public class CatHobby {
    private String what;

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    @Override
    public String toString() {
        return "CatHobby{" +
                "what='" + what + '\'' +
                '}';
    }
}