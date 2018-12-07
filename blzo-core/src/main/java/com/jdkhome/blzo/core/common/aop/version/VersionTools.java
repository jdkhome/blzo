package com.jdkhome.blzo.core.common.aop.version;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * author link.ji
 * createTime 下午5:19 2018/6/19
 * 版本控制器
 */
@Component
public class VersionTools {

   /* @Value("${current_version}")
    String currentVersion;*/

    /**
     * 检查版本
     *
     * @param userVersion
     * @param minVersion
     * @return
     */
    public boolean checkVersion(String userVersion, String minVersion) {

        if (StringUtils.isEmpty(userVersion)) {
            return false;
        }

        try {
            String[] userVersionStrArray = userVersion.split("\\.");
            String[] minVersionStrArray = minVersion.split("\\.");

            if (Integer.parseInt(userVersionStrArray[0]) < Integer.parseInt(minVersionStrArray[0])) {
                return false;
            }

            if ((Integer.parseInt(userVersionStrArray[0]) == Integer.parseInt(minVersionStrArray[0])) && (Integer.parseInt(userVersionStrArray[1]) < Integer.parseInt(minVersionStrArray[1]))) {
                return false;
            }

            if ((Integer.parseInt(userVersionStrArray[0]) == Integer.parseInt(minVersionStrArray[0])) && (Integer.parseInt(userVersionStrArray[1]) == Integer.parseInt(minVersionStrArray[1])) && (Integer.parseInt(userVersionStrArray[2]) < Integer.parseInt(minVersionStrArray[2]))) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
