package jp.sf.amateras.cookiesession.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

}
