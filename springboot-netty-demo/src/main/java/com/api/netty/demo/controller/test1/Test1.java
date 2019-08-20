package com.api.netty.demo.controller.test1;

import com.api.netty.demo.config.router.RequestMapping;
import com.api.netty.demo.bean.vo.GeneralResponse;
import io.netty.handler.codec.http.FullHttpRequest;

/**
* @Description:    作用描述
* @Author:         QianJiaXiang
* @CreateDate:     2019-08-19 17:37
*/
public class Test1 {
    @RequestMapping(uri = "/sayNice",method = "POST")
    public GeneralResponse sayNice(FullHttpRequest request){

        return new GeneralResponse("say Nice");
    }
}
