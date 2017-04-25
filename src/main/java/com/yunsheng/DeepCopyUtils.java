package com.yunsheng;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

/**
 * Created by shengyun on 17/4/7.
 */
public class DeepCopyUtils {

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
    public static void copyProperties(Object source, Object target)
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
                        // 如果是集合类型
                        if (Collection.class.isAssignableFrom(sourcePd.getPropertyType())) {
                            // 相同类型，直接复制即可
                            Field srcField = source.getClass().getDeclaredField(sourcePd.getName());
                            Field destField = target.getClass().getDeclaredField(targetPd.getName());
                            Class destTrueType = getTrueType(destField);
                            Class srcTrueType = getTrueType(srcField);
                            if (!destTrueType.equals(srcTrueType)) {
                                copyCollection(sourcePd, targetPd, source, target);
                                continue;
                            }
                        }

                        // Map
                        if (Map.class.isAssignableFrom(sourcePd.getPropertyType())) {
                            // 相同类型，直接复制即可
                            Field srcField = source.getClass().getDeclaredField(sourcePd.getName());
                            Field destField = target.getClass().getDeclaredField(targetPd.getName());
                            Class destTrueType = getMapValueTrueType(destField);
                            Class srcTrueType = getMapValueTrueType(srcField);
                            if (!destTrueType.equals(srcTrueType)) {
                                copyMap(sourcePd, targetPd, source, target);
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

    /**
     * copyMap仅支持HashMap<String, Object>
     * @param sourcePd
     * @param targetPd
     * @param source
     * @param target
     * @throws NoSuchFieldException
     */
    private static void copyMap(PropertyDescriptor sourcePd, PropertyDescriptor targetPd, Object source, Object target)
        throws NoSuchFieldException {
        Field srcField = source.getClass().getDeclaredField(sourcePd.getName());
        Field destField = target.getClass().getDeclaredField(targetPd.getName());

        srcField.setAccessible(true);
        destField.setAccessible(true);
        try {
            Class destTrueField = getMapValueTrueType(destField);
            HashMap<String, Object> srcMap = (HashMap) srcField.get(source);
            // Map 实例化 HashMap
            // 后续可以考虑配置化支持更高类型
            Map<String, Object> destMap = new HashMap();

            for (Map.Entry<String, Object> entry : srcMap.entrySet()){
                Object destObj = destTrueField.newInstance();
                copyProperties(entry.getValue(), destObj);
                destMap.put(entry.getKey(), destObj);
            }

            destField.set(target, destMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void copyCollection(PropertyDescriptor sourcePd, PropertyDescriptor targetPd, Object source, Object target)
        throws Exception {
        Field srcField = source.getClass().getDeclaredField(sourcePd.getName());
        Field destField = target.getClass().getDeclaredField(targetPd.getName());

        srcField.setAccessible(true);
        destField.setAccessible(true);
        try {
            Class destTrueField = getTrueType(destField);
            Collection srcList = (Collection) srcField.get(source);
            // List 实例化 ArrayList，Set实例化HashSet
            // 后续可以考虑配置化支持更高类型
            Collection destCollec = null;
            if(List.class.isAssignableFrom(destField.getType())){
                destCollec = new ArrayList();
            }else if(Set.class.isAssignableFrom(destField.getType())){
                destCollec = new HashSet();
            }else {
                throw new Exception("don not support type");
            }
            Iterator iterator = srcList.iterator();
            while (iterator.hasNext()) {
                Object srcObj = iterator.next();
                Object destObj = destTrueField.newInstance();
                copyProperties(srcObj, destObj);
                destCollec.add(destObj);
            }

            destField.set(target, destCollec);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static Class getTrueType(Field srcField) {
        Type genericType = srcField.getGenericType();
        ParameterizedType pt = (ParameterizedType) genericType;
        Class actulClass = (Class) pt.getActualTypeArguments()[0];
        return actulClass;

    }

    private static Class getMapValueTrueType(Field srcField) {
        Type genericType = srcField.getGenericType();
        ParameterizedType pt = (ParameterizedType) genericType;
        Class actulClass = (Class) pt.getActualTypeArguments()[1];
        return actulClass;

    }

    public static void copyList(String srcFieldStr, String destFieldStr, Object source, Object target) throws NoSuchFieldException {
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
}
