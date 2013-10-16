package jp.sf.amateras.cookiesession.util;


public class StringUtil {

	public static boolean isEmpty(String value){
		return value == null || value.trim().length() == 0;
	}

	public static boolean isNotEmpty(String value){
		return !isEmpty(value);
	}

}
