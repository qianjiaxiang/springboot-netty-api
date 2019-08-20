package com.api.netty.demo.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.api.netty.demo.config.commons.ConstantsProperties;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description:    解析Netty FullRequest请求参数
* @Author:         QianJiaXiang
* @CreateDate:     2019-08-19 18:36
*/
public class RequestUtil {
    private static final String X_WWW_FORM_URLENCODED = "x-www-form-urlencoded";
    private static final String JSON = "application/json";
    private static final String MULTIPART_FORM_DATA="multipart/form-data";
    private static final Log log = LogFactory.get();


    /**
    * @Description:    处理Get方式的请求
    * @Author:         QianJiaXiang
    * @CreateDate:     2019-08-19 18:38
    */
    public static Map<String,Object> getGetMethodParam(FullHttpRequest request){
        Map<String, Object> params = new HashMap<String, Object>();

        if (request.method() == HttpMethod.GET) {
            // 处理get请求
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
            Map<String, List<String>> paramList = decoder.parameters();
            for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
                params.put(entry.getKey(), entry.getValue().get(0));
            }
            return params;
        } else {
            return null;
        }
    }

    /**
    * @Description:    获取POST方式传递的参数
    * @Author:         QianJiaXiang
    * @CreateDate:     2019-08-19 18:41
    */
    public static Map<String, Object> getPostMethodParam(FullHttpRequest fullHttpRequest) {

        Map<String, Object> params = new HashMap<String, Object>();

        if (fullHttpRequest.method() == HttpMethod.POST) {
            // 处理POST请求
            String strContentType = fullHttpRequest.headers().get("Content-Type").trim();
            if (strContentType.contains(X_WWW_FORM_URLENCODED)) {
                params  = getFormParams(fullHttpRequest);
            } else if (strContentType.contains(JSON)) {
                try {
                    params = getJSONParams(fullHttpRequest);
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            } else if (strContentType.contains(MULTIPART_FORM_DATA)){
                params = getFormDataParams(fullHttpRequest);
            }else {
                return null;
            }
            return params;
        } else {
            return null;
        }
    }

    /**
    * @Description:    解析multipart/fom-data提交数据
    * @Author:         QianJiaXiang
    * @CreateDate:     2019-08-20 9:23
    */
    private static Map<String, Object> getFormDataParams(FullHttpRequest fullHttpRequest)  {
        Map<String,Object> params = new HashMap<>();
        ByteBuf content = fullHttpRequest.content();
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), fullHttpRequest);
        List<InterfaceHttpData> bodyHttpDatas = decoder.getBodyHttpDatas();
        for(InterfaceHttpData data:bodyHttpDatas){
            if(data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload){
                MemoryFileUpload fileUpload = (MemoryFileUpload) data;
                try {
                    File newFile = new File(ConstantsProperties.getFILE_PATH_UPLOAD() +fileUpload.getFilename());
                    boolean result = fileUpload.renameTo(newFile);
                    if(result){
                        params.put("fileName", fileUpload.getFilename());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e);
                }

            }else if(data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute){
                MemoryAttribute attribute = (MemoryAttribute) data;
                params.put(attribute.getName(), attribute.getValue());
            }
        }
        return params;
    }

    /**
   * @Description:    解析from表单数据（Content-Type = x-www-form-urlencoded）
   * @Author:         QianJiaXiang
   * @CreateDate:     2019-08-19 18:41
   */
    private static Map<String, Object> getFormParams(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<String, Object>();

        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), fullHttpRequest);
        List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();

        for (InterfaceHttpData data : postData) {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                MemoryAttribute attribute = (MemoryAttribute) data;
                params.put(attribute.getName(), attribute.getValue());
            }
        }

        return params;
    }

    /**
    * @Description:    解析json数据（Content-Type = application/json）
    * @Author:         QianJiaXiang
    * @CreateDate:     2019-08-19 18:41
    */
    private static Map<String, Object> getJSONParams(FullHttpRequest fullHttpRequest) throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<String, Object>();

        ByteBuf content = fullHttpRequest.content();
        byte[] reqContent = new byte[content.readableBytes()];
        content.readBytes(reqContent);
        String strContent = new String(reqContent, "UTF-8");
        JSONObject jsonParams = JSONObject.fromObject(strContent);
        for (Object key : jsonParams.keySet()) {
            params.put(key.toString(), jsonParams.get(key));
        }

        return params;
    }

    private FullHttpResponse responseOK(HttpResponseStatus status, ByteBuf content) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        if (content != null) {
            response.headers().set("Content-Type", "text/plain;charset=UTF-8");
            response.headers().set("Content_Length", response.content().readableBytes());
        }
        return response;
    }


}
