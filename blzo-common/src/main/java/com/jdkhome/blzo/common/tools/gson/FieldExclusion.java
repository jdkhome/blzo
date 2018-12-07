package com.jdkhome.blzo.common.tools.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldExclusion implements ExclusionStrategy {
    String[] inStr;

    public FieldExclusion(String[] outStr) {
        this.inStr = outStr;
    }

    /**
     * 
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
     *
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
        boolean flag = false;

        for (int i = 0; i < inStr.length; i++) {
            // 手动排除inStr串指定字段
            if (f.getName().equals(inStr[i])) {
                flag = true;
                log.debug("排除字段===============>{}",f.getName());
            } else {
                // 如果属性带有MyExclus 注解，则排除
                flag = f.getAnnotation(MyExclus.class) != null;
            }
        }

        return flag;
    }

}