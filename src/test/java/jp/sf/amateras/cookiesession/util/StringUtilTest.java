package jp.sf.amateras.cookiesession.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testIsEmpty() {
		assertTrue(StringUtil.isEmpty(null));
		assertTrue(StringUtil.isEmpty(""));
		assertTrue(StringUtil.isEmpty(" "));
		assertTrue(StringUtil.isEmpty("\t"));
		assertFalse(StringUtil.isEmpty("a"));
	}

	@Test
	public void testIsNotEmpty() {
		assertFalse(StringUtil.isNotEmpty(null));
		assertFalse(StringUtil.isNotEmpty(""));
		assertFalse(StringUtil.isNotEmpty(" "));
		assertFalse(StringUtil.isNotEmpty("\t"));
		assertTrue(StringUtil.isNotEmpty("a"));
	}

	@Test
	public void testSplit() {
		String value = "12345678";
		List<String> result = StringUtil.split(value, 4);

		assertEquals(2, result.size());
		assertEquals("1234", result.get(0));
		assertEquals("5678", result.get(1));
	}

}
