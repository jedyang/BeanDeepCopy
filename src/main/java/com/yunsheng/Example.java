package com.yunsheng;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shengyun on 17/2/20.
 */
public class Example {


    public static void main(String[] args) {
//        SpringApplication.run(Example.class, args);

//        System.out.println(String.class.isAssignableFrom(Object.class));
//        System.out.println(Object.class.isAssignableFrom(String.class));
//        System.out.println(List.class.isAssignableFrom(ArrayList.class));
//        System.out.println(List.class.isAssignableFrom(List.class));
        System.out.println(ArrayList.class.isAssignableFrom(List.class));

        Cat cat = new Cat();
        cat.setName("cat");
        cat.setLength(10L);
        List<CatHobby> hobby = new ArrayList<CatHobby>();
        CatHobby one = new CatHobby();
        one.setWhat("miao");
        CatHobby two = new CatHobby();
        two.setWhat("two");
        hobby.add(one);
        hobby.add(two);
        cat.setHobby(hobby);

        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("1", "100100");
        cat.setMyMap(myMap);

        Dog dog = new Dog();
        copyProperties(cat, dog);
//        System.out.println(dog);
//        CatHobby three = new CatHobby();
//        three.setWhat("three");
//        cat.getHobby().add(three);
        System.out.println(cat);


//        copyBean(cat, dog);
        System.out.println(dog);

    }


    private static Class getTrueType(Field srcField) {
        Type genericType = srcField.getGenericType();
        ParameterizedType pt = (ParameterizedType) genericType;
        Class actulClass = (Class) pt.getActualTypeArguments()[0];
        return actulClass;

    }

    private static void copyList(String srcFieldStr, String destFieldStr, Object source, Object target) throws NoSuchFieldException {
        Field srcField = source.getClass().getDeclaredField(srcFieldStr);
        Field destField = target.getClass().getDeclaredField(destFieldStr);

        srcField.setAccessible(true);
        destField.setAccessible(true);
        try {
            Class destTrueField = getTrueType(destField);
            List srcList = (List) srcField.get(source);
            List destList = new ArrayList();
            for (int j = 0; j < srcList.size(); j++) {
                Object srcObj = srcList.get(j);
                Object destObj = destTrueField.newInstance();
                copyProperties(srcObj, destObj);
                destList.add(destObj);
            }
            destField.set(target, destList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    /**
     * 不支持基本类型
     * 支持List
     * 支持简单类型hashMap
     * <p>
     * 深层次复制。
     * 当目标子级元素和源自己元素都是自定义类型，并且类型不相同时，递归copy
     *
     * @param source
     * @param target
     * @throws BeansException
     * @author <a href="mailto:shengyun@taobao.com">shengyun</a>
     * @since 2017年2月21日
     */
    private static void copyProperties(Object source, Object target)
            throws BeansException {

        Class actualEditable = target.getClass();

        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);

        for (int i = 0; i < targetPds.length; i++) {
            PropertyDescriptor targetPd = targetPds[i];
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object srcValue = readMethod.invoke(source, new Object[0]);
                        if (null == srcValue) {
                            continue;
                        }
                        // 如果是List类型

                        if (List.class.isAssignableFrom(sourcePd.getPropertyType())) {
                            // 相同类型，直接复制即可
                            Field srcField = source.getClass().getDeclaredField(sourcePd.getName());
                            Field destField = target.getClass().getDeclaredField(targetPd.getName());
                            if (!getTrueType(destField).equals(getTrueType(srcField))) {
                                copyList(sourcePd.getName(), targetPd.getName(), source, target);
                                continue;
                            }
                        }


                        // 如果前后两者类型不同，不是父子类型，dest是src的父类型也是可以的。
//                        boolean sameClass = srcValue.getClass().getName().equals(targetPd.getPropertyType().getName());
                        // AA.class.isAssignableFrom(BB.class) AA是否是BB的父类或一样的。
                        boolean sameClass = targetPd.getPropertyType().isAssignableFrom(srcValue.getClass());
                        if (!sameClass) {
                            // 如果不是基本包装类型和String
                            // 自己保证，基本类型的要对应一致
                            boolean base = srcValue instanceof String || srcValue instanceof Number || srcValue instanceof Boolean;
                            if (!base) {

                                Object dstValue = targetPd.getPropertyType().newInstance();
                                copyProperties(srcValue, dstValue);

                                Method writeMethod = targetPd.getWriteMethod();
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, new Object[]{dstValue});

                            }

                        } else {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, new Object[]{srcValue});
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

}
