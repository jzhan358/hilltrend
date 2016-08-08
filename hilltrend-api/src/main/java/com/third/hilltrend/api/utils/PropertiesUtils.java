package com.third.hilltrend.api.utils;


import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrs on 2014/7/25.
 */
public final class PropertiesUtils<S, D> {

    private PropertiesUtils() {

    }


    public static <S, D> S copy(Class<S> clazz, D source) {
        if (null == source) {
            return null;
        }
        try {
            S target = clazz.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("PropertiesUtils copy error", e);
        }
    }


    public static <S, D> List<D> copyList(Class<D> clazz, List<S> sourceList) {
        List<D> targetList = new ArrayList<D>();
        try {
            for (S item : sourceList) {
                D target = clazz.newInstance();
                BeanUtils.copyProperties(item, target);
                if (!targetList.contains(target)) {
                    targetList.add(target);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("PropertiesUtils copyList error", e);
        }
        return targetList;
    }

    /**
     * 集合拷贝(排重)
     * @param clazz
     * @param sourceList
     * @param ignoreProperties
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> List<D> copyList(Class<D> clazz, List<S> sourceList, String... ignoreProperties) {
        List<D> targetList = new ArrayList<D>();
        try {
            for (S item : sourceList) {
                D target = clazz.newInstance();
                BeanUtils.copyProperties(item, target, ignoreProperties);
                if (!targetList.contains(target)) {
                    targetList.add(target);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("PropertiesUtils copyList error", e);
        }
        return targetList;
    }

    /**
     * 集合拷贝(不排重)
     * @param clazz
     * @param sourceList
     * @param ignoreProperties
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> List<D> copyListNotExcludeRepeat(Class<D> clazz, List<S> sourceList, String... ignoreProperties) {
        List<D> targetList = new ArrayList<D>();
        try {
            for (S item : sourceList) {
                D target = clazz.newInstance();
                BeanUtils.copyProperties(item, target, ignoreProperties);
                targetList.add(target);
            }
        } catch (Exception e) {
            throw new RuntimeException("PropertiesUtils copyList error", e);
        }
        return targetList;
    }


    public static <S, T> S copy(Class<S> clazz, T source, String... ignoreProperties) {
        if (null == source) {
            return null;
        }
        try {
            S target = clazz.newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("PropertiesUtils copy error", e);
        }
    }
}
