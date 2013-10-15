package jp.sf.amateras.cookiesession.primitive;

import java.util.Locale;

/**
 *
 *
 * @author Naoki Takezoe
 */
public class PrimitiveWrapperUtil {

	/**
	 * Wraps the wrapper object (or string) by the container to JSON encoding.
	 *
	 * @param value the value to convert to JSON
	 * @return the wrapped value if it's wrapper object or string, otherwise the raw value
	 */
	public static Object wrap(Object value){

		if(value.getClass().isArray()){
			return new ObjectArrayContainer(
					value.getClass().getComponentType().getName(), (Object[]) value);

		} else if(value instanceof Boolean){
			return new BooleanContainer((Boolean) value);

		} else if(value instanceof Byte){
			return new ByteContainer((Byte) value);

		} else if(value instanceof Character){
			return new CharacterContainer((Character) value);

		} else if(value instanceof Double){
			return new DoubleContainer((Double) value);

		} else if(value instanceof Float){
			return new FloatContainer((Float) value);

		} else if(value instanceof Integer){
			return new IntegerContainer((Integer) value);

		} else if(value instanceof Long){
			return new LongContainer((Long) value);

		} else if(value instanceof Short){
			return new ShortContainer((Short) value);

		} else if(value instanceof String){
			return new StringContainer((String) value);

		} else if(value instanceof Locale){
			return new LocaleContainer((Locale) value);
		}

		return value;
	}

	/**
	 * Retrieves the wrapped value from the container.
	 *
	 * @param value the container or any other object
	 * @return unwrapped value the unwrapped value if the given value is container, otherwise the raw value
	 */
	public static Object getValue(Object value){

		if(value instanceof ObjectArrayContainer){
			return ((ObjectArrayContainer) value).getArray();

		} else if(value instanceof BooleanContainer){
			return ((BooleanContainer) value).value;

		} else if(value instanceof ByteContainer){
			return ((ByteContainer) value).value;

		} else if(value instanceof CharacterContainer){
			return ((CharacterContainer) value).value;

		} else if(value instanceof DoubleContainer){
			return ((DoubleContainer) value).value;

		} else if(value instanceof FloatContainer){
			return ((FloatContainer) value).value;

		} else if(value instanceof IntegerContainer){
			return ((IntegerContainer) value).value;

		} else if(value instanceof LongContainer){
			return ((LongContainer) value).value;

		} else if(value instanceof ShortContainer){
			return ((ShortContainer) value).value;

		} else if(value instanceof StringContainer){
			return ((StringContainer) value).value;

		} else if(value instanceof LocaleContainer){
			return ((LocaleContainer) value).value;
		}

		return value;
	}

}
