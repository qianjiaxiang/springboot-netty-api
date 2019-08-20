/*   
 * @Title: ReadPropertiesUtils.java
 * @Package com.sptl.utils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author LuRh
 * @date 2014年8月25日 下午2:54:35
 * @version V1.0
 * @company vsc
 */
package com.api.netty.demo.config.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @ClassName:ReadPropertiesUtils.java
 * @Description:读取配置文件
 * @version v1.0.0
 * @author skyengine
 * @date 2019年8月6日 上午9:51:05
 * Modification History
 * Date				Author				Version			Description
 * ----------------------------------------------------------------
 * 2019年8月6日		skyengine			v1.0.0			修改原因
 */
public class ReadPropertiesUtils extends Properties {

	//后台打印LOG信息
	private static Logger logger= LogManager.getLogger(ReadPropertiesUtils.class);
    private static final long serialVersionUID = -1711289556692016113L;
    private static ReadPropertiesUtils msgInstance;

    public static Properties getInstance() {
        if (null != msgInstance) {
            return msgInstance;
        } else {
            makeInstance();
            return msgInstance;
        }
    }

    private static synchronized void makeInstance() {
        if (msgInstance == null) {
            msgInstance = new ReadPropertiesUtils();
        }
    }

    private ReadPropertiesUtils() {
//    	InputStream configInputStream = getClass().getResourceAsStream("/config.properties");
//        InputStream messageInputStream = getClass().getResourceAsStream("/message.properties");
        InputStream errmsgInputStream = getClass().getResourceAsStream("/errmsg.properties");
        try {
//            this.load(configInputStream);
//            this.load(messageInputStream);
            this.load(errmsgInputStream);
        } catch (IOException e) {
        	logger.error(" ReadPropertiesUtils IOException : "+e.getMessage());
            e.printStackTrace();
        }
    }

}
