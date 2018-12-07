package com.jdkhome.blzo.common.pojo;

import lombok.Data;

/**
 * Created by jdk on 2017/9/4.
 */
@Data
public class PageRequest {

    private Integer page;
    private Integer size;

    public PageRequest() {
        // 默认获取第一页的20个数据
        this.page = 1;
        this.size = 20;
    }

    public PageRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
}
