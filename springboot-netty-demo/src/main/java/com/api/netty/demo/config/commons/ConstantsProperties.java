package com.api.netty.demo.config.commons;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
* @Description:    将properties配置文件中值转换为静态变量
* @Author:         QianJiaXiang
* @CreateDate:     2019-08-20 11:07
*/

public class ConstantsProperties {
    @Value("file_path_upload")
    private String filePathUpload;
    @Getter
    public static String FILE_PATH_UPLOAD;


}
