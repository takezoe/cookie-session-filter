package jp.sf.amateras.cookiesession.encoder;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterConfig;

import jp.sf.amateras.cookiesession.exception.CookieSessionException;
import jp.sf.amateras.cookiesession.exception.EncoderException;
import jp.sf.amateras.cookiesession.exception.InitializationException;
import jp.sf.amateras.cookiesession.primitive.PrimitiveWrapperUtil;
import net.arnx.jsonic.JSON;

/**
 * An implementation of {@link SessionEncoder} which encodes and decodes session attributes as JSON string.
 *
 * @author Naoki Takezoe
 */
public class JSONEncoder implements SessionEncoder {

	public void init(FilterConfig config) throws InitializationException {
	}

	public String encode(Map<String, Object> attributes) throws EncoderException {
		try {
			Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();

			for(Map.Entry<String, Object> entry: attributes.entrySet()){
				String name = entry.getKey();
				Object value = PrimitiveWrapperUtil.wrap(entry.getValue());

				Map<String, String> info = new HashMap<String, String>();
				info.put("type", value.getClass().getName());
				info.put("json", JSON.encode(value));

				map.put(name, info);
			}

			return JSON.encode(map);

		} catch(Exception ex){
			throw new EncoderException(ex);
		}
	}

	public Map<String, Object> decode(String encodedValue) throws EncoderException {
		try {
			@SuppressWarnings("unchecked")
			Map<String, Map<String, String>> map = (Map<String, Map<String, String>>) JSON.decode(encodedValue);
			Map<String, Object> attributes = new HashMap<String, Object>();
			for(Map.Entry<String, Map<String, String>> entry: map.entrySet()){
				String name = entry.getKey();
				Map<String, String> info = entry.getValue();
				String type = info.get("type");
				String json = info.get("json");

				try {
					Object value = PrimitiveWrapperUtil.getValue(JSON.decode(json, Class.forName(type)));
					attributes.put(name, value);

				} catch(ClassNotFoundException ex){
					throw new CookieSessionException(ex);
				}
			}

			return attributes;

		} catch(Exception ex){
			throw new EncoderException(ex);
		}
	}

}
