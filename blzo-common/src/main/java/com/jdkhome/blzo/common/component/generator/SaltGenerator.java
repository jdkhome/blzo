package com.jdkhome.blzo.common.component.generator;

import java.util.UUID;

/**
 * Created by jdk on 17/11/20.
 */
public class SaltGenerator {

    public static String generateUUIDSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
