package com.api.netty.demo.config.commons;

import java.text.MessageFormat;

/**
 * 
 * @ClassName:Message.java
 * @Description:取得报错信息内容
 * @version v1.0.0
 * @author skyengine
 * @date 2019年8月6日 上午9:50:55
 * Modification History
 * Date				Author				Version			Description
 * ----------------------------------------------------------------
 * 2019年8月6日		skyengine			v1.0.0			修改原因
 */
public class Message {
	public static String getMessage(String messageId, String... messageParams) {
		String message = ReadPropertiesUtils.getInstance().getProperty(messageId);
		if (messageParams == null) {
			messageParams = new String[] {};
		}
		message = MessageFormat.format(message, (Object[]) messageParams);
		return message;
	}
}
