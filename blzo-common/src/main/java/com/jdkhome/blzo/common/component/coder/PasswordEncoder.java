package com.jdkhome.blzo.common.component.coder;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by jdk on 17/11/20.
 * 密码加密器
 */
public class PasswordEncoder {

    /**
     * MD5 加密
     *
     * @param password
     * @param salt
     * @return
     */
    public static String toMD5(String password, String salt) {
        String encodedPassword = DigestUtils.md5Hex(mergePasswordAndSalt(password, salt, false));
        return encodedPassword;
    }

    /**
     * 摘自 spring security 的 加盐函数
     * <p>
     * Used by subclasses to generate a merged password and salt <code>String</code>.
     * <P>
     * The generated password will be in the form of <code>password{salt}</code>.
     * </p>
     * <p>
     * A <code>null</code> can be passed to either method, and will be handled correctly.
     * If the <code>salt</code> is <code>null</code> or empty, the resulting generated
     * password will simply be the passed <code>password</code>. The <code>toString</code>
     * method of the <code>salt</code> will be used to represent the salt.
     * </p>
     *
     * @param pwdStr the password to be used (can be <code>null</code>)
     * @param salt     the salt to be used (can be <code>null</code>)
     * @param strict   ensures salt doesn't contain the delimiters
     * @return a merged password and salt <code>String</code>
     * @throws IllegalArgumentException if the salt contains '{' or '}' characters.
     */
    private static String mergePasswordAndSalt(String pwdStr, Object salt, boolean strict) {
        if (pwdStr == null) {
            pwdStr = "";
        }

        if (strict && (salt != null)) {
            if ((salt.toString().lastIndexOf("{") != -1)
                    || (salt.toString().lastIndexOf("}") != -1)) {
                throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
            }
        }

        if ((salt == null) || "".equals(salt)) {
            return pwdStr;
        } else {
            return pwdStr + "{" + salt.toString() + "}";
        }
    }

}
