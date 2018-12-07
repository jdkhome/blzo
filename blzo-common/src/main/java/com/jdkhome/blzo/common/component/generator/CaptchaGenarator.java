package com.jdkhome.blzo.common.component.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by jdk on 17/5/5.
 * 验证码生成器
 */
@Component
public class CaptchaGenarator {


    /**
     * 生成指定位数的随机验证码
     *
     * @param length 验证码位数
     * @reuslt 生成的随机验证码
     */
    public static String generate(int length) {
        String chars = "0123456789";
        char[] rands = new char[length];
        for (int i = 0; i < length; i++) {
            rands[i] = chars.charAt(new Random().nextInt(10));
        }
        return String.valueOf(rands);
    }

    public static String generateSixCaptcha() {
        return CaptchaGenarator.generate(6);
    }

}
