package com.api.netty.demo.config.router;

import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
* @Description:    通过uri及http method进行路由
* @Author:         QianJiaXiang
* @CreateDate:     2019-08-19 16:08
*/
@Data
@AllArgsConstructor
public class HttpLabel {
    private String uri;
    private HttpMethod method;
}
