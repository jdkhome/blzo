package com.jdkhome.blzo.common.rpc;

import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author linkji
 * @date 2019-03-27 12:15
 */
@FeignClient(name = "blzo-microservice-k8s-app-user-service", url = "${feign.app-user}")
public interface UserRPCService {

    @RequestMapping(value = "/api/user/add", method = RequestMethod.POST)
    ApiResponse apiUserAdd(@RequestParam("name") String name,
                           @RequestParam("phone") String phone);

}




















