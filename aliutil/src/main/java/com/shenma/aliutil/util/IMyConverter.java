package com.shenma.aliutil.util;

import java.lang.reflect.Type;

import org.apache.commons.beanutils.Converter;

public interface IMyConverter extends Converter {

	public <T> T convert(Class<T> type, Object value,Type[] tArgs);
}
