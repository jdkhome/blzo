package com.jdkhome.blzo.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by jdk on 2019/3/1.
 */
public class StringTools {
    public static String listToStr(List list) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        list.forEach(obj -> {
            if (obj != null) {
                sb.append(obj.toString()).append(",");
            }
        });
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : sb.toString();
    }
}
