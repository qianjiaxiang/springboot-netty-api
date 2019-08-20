package com.api.netty.demo.controller.test;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.api.netty.demo.bean.vo.GeneralResponse;
import com.api.netty.demo.bean.vo.Response;
import com.api.netty.demo.config.router.RequestMapping;
import com.api.netty.demo.util.RequestUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

import java.awt.geom.GeneralPath;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skyengine
 */

public class TestController {

    @RequestMapping(uri = "/test",method = "POST")
    public GeneralResponse test(FullHttpRequest request){

        //将post请求的参数返回，如果包含了文件上传的内容，保存到配置的文件夹下面，并返回文件名
        Map<String, Object> postMethodParam = RequestUtil.getPostMethodParam(request);
        if(MapUtil.isNotEmpty(postMethodParam)){
            System.out.println("postParam:"+postMethodParam.toString());
        }
        return new GeneralResponse("test");
    }
    @RequestMapping(uri = "/testGet",method = "GET")
    public GeneralResponse testGet(FullHttpRequest request){
        Map<String, Object> getMethodParam = RequestUtil.getGetMethodParam(request);
        if(MapUtil.isNotEmpty(getMethodParam)){
            System.out.println("getMethodParam:"+getMethodParam);
        }
        return new GeneralResponse("testGet");
    }

    private Map<String, Object> getRequestParams(FullHttpRequest request) {
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request);
        List<InterfaceHttpData> httpPostData = decoder.getBodyHttpDatas();
        Map<String, Object> params = new HashMap<>();

        for (InterfaceHttpData data : httpPostData) {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                MemoryAttribute attribute = (MemoryAttribute) data;
                params.put(attribute.getName(), attribute.getValue());
            }
        }
        return params;
    }
}
