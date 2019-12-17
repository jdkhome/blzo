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
@FeignClient(name = "blzo-microservice-k8s-app-friend-service", url = "${feign.app-friend}")
public interface FriendRPCService {

    @RequestMapping(value = "/api/friend/add", method = RequestMethod.POST)
    ApiResponse apiFriendAdd(@RequestParam("name") String name,
                             @RequestParam("phone") String phone);

}




















