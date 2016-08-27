/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.alibaba.openapi.client.util;

import com.alibaba.openapi.client.exception.AuthServiceException;
import com.alibaba.openapi.client.exception.InvokeConnectException;
import com.alibaba.openapi.client.exception.InvokeTimeoutException;
import com.alibaba.openapi.client.exception.OceanException;
import com.alibaba.openapi.client.exception.SecurityException;
import com.alibaba.openapi.client.exception.UnsupportAPIException;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class ExceptionParser {
	public static Throwable buildException4Json2(Object exption) {
		Map m = null;
		if (exption instanceof Map)
			m = (Map) exption;
		else {
			try {
				m = JsonMapper.json2map(exption.toString());
			} catch (Exception e) {
				return buildException(0, exption.toString());
			}
		}

		String errorCodeStr = (String) m.get("error_code");
		String errorMesage = (String) m.get("error_message");

		if (StringUtils.isBlank(errorMesage))
			try {
				errorMesage = JsonMapper.pojo2json(m);
			} catch (IOException e) {
			}
		if ((StringUtils.isBlank(errorCodeStr))
				|| (!(StringUtils.isNumeric(errorCodeStr)))) {
			return buildException(errorCodeStr, errorMesage);
		}
		int errorCode = Integer.parseInt(errorCodeStr);
		return buildException(errorCode, errorMesage);
	}

	public static Throwable buildException4OAuth2(Object exption) {
		Map m = (Map) exption;

		int errorCode = 401;
		String errorMesage = (String) m.get("error_description");
		return buildException(errorCode, errorMesage);
	}

	private static Throwable buildException(int errorCode, String errorMesage) {
		switch (errorCode) {
		case 400:
			return new SecurityException(errorMesage);
		case 401:
			return new AuthServiceException(errorMesage);
		case 404:
			return new UnsupportAPIException(errorMesage);
		case 502:
			return new InvokeConnectException(errorMesage);
		case 504:
			return new InvokeTimeoutException(errorMesage);
		}
		return new OceanException(String.valueOf(errorCode), errorMesage);
	}

	private static Throwable buildException(String errorCode, String errorMesage) {
		return new OceanException(errorCode, errorMesage);
	}
}