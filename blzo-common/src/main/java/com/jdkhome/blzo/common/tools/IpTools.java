package com.jdkhome.blzo.common.tools;

import com.jdkhome.blzo.common.tools.ip_tool.MyDbSearcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Slf4j
public class IpTools {
    private IpTools() {
    }
    //日志

    private static class HolderClass {
        static MyDbSearcher searcher;

        public static void inputstreamtofile(InputStream ins, File file) throws Exception {
            OutputStream os = null;

            try {
                os = new FileOutputStream(file);


                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            } catch (FileNotFoundException e) {
                log.error("inputstreamtofile -> error:{}",e);
            } finally {

                if (null != os) {
                    os.close();
                }
                if (null != ins) {
                    ins.close();
                }
            }

        }

        static {
            try {
                DbConfig config = new DbConfig();

                InputStream inputStream = IpTools.class.getResourceAsStream("/data/ip2region.db");
                //String dbfile = IpTools.class.getClassLoader().getResource("ip2region.db").getFile();
                File file = File.createTempFile("ipdb", ".tmp");

                inputstreamtofile(inputStream, file);

                searcher = new MyDbSearcher(config, file);
            } catch (Exception e) {
                log.error("单例初始化 -> e:", e);
            }
        }
    }


    /**
     * 获取真实的IP，尤其是经过反向代理处理过的ip
     *
     * @Return
     */
    public static String getIp(HttpServletRequest request) {

        //1 优先从X-Forwarded-For
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }

        //2 其次从X-Real-IP中获取ip
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 更具ip获取城市名
     * 拿不到城市就返回国家
     *
     * @param ip
     * @return
     */
    public static String getCity(String ip) {

        //log.info("ip -> {}", ip);


        if (StringUtils.isEmpty(ip)) {
            return "未知";
        }

        try {
            // B树搜索（更快）
            DataBlock block2 = HolderClass.searcher.btreeSearch(ip);
            String[] result = block2.getRegion().split("\\|");

            if (result.length == 5) {
                if (StringUtils.isNotEmpty(result[3]) && !result[3].equals("0")) {
                    return result[3];
                } else if (StringUtils.isNotEmpty(result[0]) && !result[0].equals("0")) {
                    return result[0];
                }
            }

        } catch (Exception e) {
            log.error("解析ip异常 -> e:", e);
        }
        return "未知";
    }

}