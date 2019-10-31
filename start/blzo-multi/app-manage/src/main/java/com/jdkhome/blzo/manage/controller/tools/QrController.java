package com.jdkhome.blzo.manage.controller.tools;

import com.jdkhome.blzo.ex.utils.tools.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * author linkji.
 * create at 2019-06-17 17:26
 */
@Slf4j
@RestController
@RequestMapping("/manage/tools/qr")
public class QrController {

    /**
     * 获取二维码
     */
    @RequestMapping("/get")
    public void find(@RequestParam(value = "content", required = true) String content,
                     HttpServletResponse response) throws Exception {

        //将图片输出给浏览器
        BufferedImage image = QRCodeUtil.createImage(content, null, false);
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }
}