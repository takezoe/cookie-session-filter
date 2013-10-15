package jp.sf.amateras.cookiesession.primitive;

import java.lang.reflect.Array;

import jp.sf.amateras.cookiesession.exception.CookieSessionException;

public class ObjectArrayContainer {

	public String type;

	public Object[] value;

	public ObjectArrayContainer(){
	}

	public ObjectArrayContainer(String type, Object[] value){
		this.type = type;
		this.value = value;
	}

	public Object getArray(){
		try {
			Object array = Array.newInstance(Class.forName(type), value.length);
			for(int i=0; i < value.length; i++){
				Array.set(array, i, value[i]);
			}
			return array;

		} catch(Exception ex){
			throw new CookieSessionException(ex);
		}
	}

}
