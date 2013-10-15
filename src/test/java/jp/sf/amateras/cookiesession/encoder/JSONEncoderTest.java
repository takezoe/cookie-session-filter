package jp.sf.amateras.cookiesession.encoder;

import static junit.framework.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class JSONEncoderTest {

	@Test
	public void testEncodeAndDecode(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("int", 1);
		map.put("array", new String[]{"aaa", "bbb"});

		JSONEncoder encoder = new JSONEncoder();

		String encoded = encoder.encode(map);
		Map<String, Object> result = encoder.decode(encoded);

		Integer intValue = (Integer) result.get("int");
		assertEquals(1, intValue.intValue());

		String[] arrayValue = (String[]) result.get("array");
		assertEquals(2, arrayValue.length);
		assertEquals("aaa", arrayValue[0]);
		assertEquals("bbb", arrayValue[1]);
	}

}
