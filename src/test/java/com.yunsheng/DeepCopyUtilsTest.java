package com.yunsheng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yunsheng.demo.Cat;
import com.yunsheng.demo.CatHobby;
import com.yunsheng.demo.Dog;
import org.junit.Test;

/**
 * Created by shengyun on 17/4/7.
 */
public class DeepCopyUtilsTest {

    @Test
    public void test_copy(){
        Cat cat = new Cat();
        cat.setName("cat");
        cat.setLength(10L);
        cat.setBoo(true);
        cat.setCatOnly(10);

        // 对象集合测试
        CatHobby one = new CatHobby();
        one.setWhat("one");
        CatHobby two = new CatHobby();
        two.setWhat("two");
        List<CatHobby> hobby = new ArrayList<CatHobby>();
        hobby.add(one);
        hobby.add(two);
        cat.setComplexList(hobby);

        Set<CatHobby> complexSet = new HashSet<CatHobby>();
        complexSet.add(one);
        complexSet.add(two);
        cat.setComplexSet(complexSet);

        Map<String, CatHobby> complexMap= new HashMap<String, CatHobby>();
        complexMap.put("one", one);
        cat.setComplexMap(complexMap);

        // 简单集合
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("1", "100100");
        cat.setMyMap(myMap);

        Set<String> mySet = new HashSet<String>();
        mySet.add("mySet");
        cat.setMySet(mySet);

        List myList = new ArrayList();
        myList.add("123");
        cat.setMyList(myList);

        Dog dog = new Dog();
        DeepCopyUtils.copyProperties(cat, dog);
        System.out.println("=====cat=====" + cat.hashCode());
        System.out.println(cat);

        System.out.println("======dog=====" + dog.hashCode());
        System.out.println(dog);
        dog.setName("dog");
        System.out.println(dog);
    }
}