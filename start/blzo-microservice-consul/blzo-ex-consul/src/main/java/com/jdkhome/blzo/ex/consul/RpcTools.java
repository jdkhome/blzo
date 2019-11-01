package com.jdkhome.blzo.ex.consul;

import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcTools {

    /**
     * 校验rpc的返回码
     *
     * @param apiResponse
     */
    public static void check(ApiResponse apiResponse) {

        if (!BasicResponseError.SUCCESS.getCode().equals(apiResponse.getCode())) {

            throw new ServiceException(apiResponse.getCode(), apiResponse.getMsg(), "[来自RPC]" + apiResponse.getDebug(),
                    BasicResponseError.UPSTREAM_ERROR);
        }
    }

    /**
     * 校验并获取请求内容
     *
     * @param apiResponse
     * @param <T>
     * @return
     */
    public static <T> T get(ApiResponse<T> apiResponse) {
        if (!BasicResponseError.SUCCESS.getCode().equals(apiResponse.getCode())) {
            throw new ServiceException(apiResponse.getCode(), apiResponse.getMsg(), "[来自RPC]" + apiResponse.getDebug(),
                    BasicResponseError.UPSTREAM_ERROR);
        }
        return apiResponse.getData();
    }
}
