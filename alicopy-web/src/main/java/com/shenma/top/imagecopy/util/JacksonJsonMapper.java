package com.shenma.top.imagecopy.util;

import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;

public final class JacksonJsonMapper {
	private static volatile ObjectMapper objectMapper = new ObjectMapper();
	static{
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
	}
	
	
	private JacksonJsonMapper() {
		
	}

	public static ObjectMapper getInstance() {
		/*if (objectMapper == null) {
			synchronized (ObjectMapper.class) {
				if (objectMapper == null) {
					objectMapper = new ObjectMapper();
				}
			}
		}*/
		return objectMapper;
	}
}
