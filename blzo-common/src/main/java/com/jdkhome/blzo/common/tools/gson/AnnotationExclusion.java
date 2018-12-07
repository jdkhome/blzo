package com.jdkhome.blzo.common.tools.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * YC 2017年8月1日 下午2:39:22
 * <p>
 * Title:排除策略
 * </p>
 * <p>
 * Description: 用于排除一个类不用转换的字段
 * </p>
 */
public class AnnotationExclusion implements ExclusionStrategy {

    /**
     * YC 2017年8月1日 下午2:41:39
     * <p>
     * Title: shouldSkipClass
     * </p>
     * <p>
     * Description: 应该排除的类
     * </p>
     *
     * @param class1
     * @return
     * @see ExclusionStrategy#shouldSkipClass(Class)
     */
    @Override
    public boolean shouldSkipClass(Class<?> class1) {
        return false;
    }

    /**
     * YC 2017年8月1日 下午2:41:57
     * <p>
     * Title: shouldSkipField
     * </p>
     * <p>
     * Description: 应该排除的字段
     * </p>
     *
     * @param f
     * @return
     * @see ExclusionStrategy#shouldSkipField(FieldAttributes)
     */
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        //如果属性带有MyExclus 注解，则排除
        return f.getAnnotation(MyExclus.class) != null;
    }

}