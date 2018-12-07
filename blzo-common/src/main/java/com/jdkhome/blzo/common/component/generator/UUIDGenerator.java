package com.jdkhome.blzo.common.component.generator;

import java.util.UUID;

/**
 * Created by jdk on 17/11/20.
 * token 生成器
 */
public class UUIDGenerator {

    public static String generateUUIDToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
