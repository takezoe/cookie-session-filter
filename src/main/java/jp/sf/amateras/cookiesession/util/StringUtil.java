package jp.sf.amateras.cookiesession.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	public static boolean isEmpty(String value){
		return value == null || value.trim().length() == 0;
	}

	public static boolean isNotEmpty(String value){
		return !isEmpty(value);
	}

	public static List<String> split(String value, int splitLength){
		List<String> list = new ArrayList<String>();
		while(value.length() > splitLength){
			list.add(value.substring(0, splitLength));
			value = value.substring(splitLength);
		}
		if(value.length() > 0){
			list.add(value);
		}
		return list;
	}

}
