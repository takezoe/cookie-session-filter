package jp.sf.amateras.cookiesession.primitive;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

public class PrimitiveWrapperUtilTest {

	@Test
	public void testWrap_Array() {
		String[] array = new String[]{"a", "b"};
		Object result = PrimitiveWrapperUtil.wrap(array);

		String[] value = (String[]) ((ObjectArrayContainer) result).getArray();
		assertEquals(2, value.length);
		assertEquals("a", value[0]);
		assertEquals("b", value[1]);
	}

	@Test
	public void testWrap_Boolean() {
		Object result = PrimitiveWrapperUtil.wrap(true);
		assertEquals(true, ((BooleanContainer) result).value.booleanValue());
	}

	@Test
	public void testWrap_Byte() {
		Object result = PrimitiveWrapperUtil.wrap((byte) 1);
		assertEquals((byte) 1, ((ByteContainer) result).value.byteValue());
	}

	@Test
	public void testWrap_Character() {
		Object result = PrimitiveWrapperUtil.wrap('A');
		assertEquals('A', ((CharacterContainer) result).value.charValue());
	}

	@Test
	public void testWrap_Double() {
		Object result = PrimitiveWrapperUtil.wrap(1.2d);
		assertEquals(1.2d, ((DoubleContainer) result).value.doubleValue(), 0);
	}

	@Test
	public void testWrap_Float() {
		Object result = PrimitiveWrapperUtil.wrap(1.2f);
		assertEquals(1.2f, ((FloatContainer) result).value.floatValue(), 0);
	}

	@Test
	public void testWrap_Integer() {
		Object result = PrimitiveWrapperUtil.wrap(1);
		assertEquals(1, ((IntegerContainer) result).value.intValue());
	}

	@Test
	public void testWrap_Long() {
		Object result = PrimitiveWrapperUtil.wrap(123l);
		assertEquals(123l, ((LongContainer) result).value.longValue());
	}

	@Test
	public void testWrap_Short() {
		Object result = PrimitiveWrapperUtil.wrap((short) 1);
		assertEquals((short) 1, ((ShortContainer) result).value.shortValue());
	}

	@Test
	public void testWrap_String() {
		TestBean bean = new TestBean();
		Object result = PrimitiveWrapperUtil.wrap(bean);
		assertSame(bean, result);
	}

	@Test
	public void testWrap_Locale() {
		Locale locale = new Locale("ja_JP");
		Object result = PrimitiveWrapperUtil.wrap(locale);
		assertEquals(new Locale("ja_JP"), ((LocaleContainer) result).value);
	}

	@Test
	public void testWrap_JavaBean() {
		Object result = PrimitiveWrapperUtil.wrap("abc");
		assertEquals("abc", ((StringContainer) result).value);
	}

	@Test
	public void testGetValue_Array() {
		Integer[] array = new Integer[]{1, 2, 3};
		ObjectArrayContainer container = new ObjectArrayContainer(
				"java.lang.Integer", (Object[]) array);

		Integer[] result = (Integer[]) PrimitiveWrapperUtil.getValue(container);
		assertEquals(3, result.length);
		assertEquals(1, result[0].intValue());
		assertEquals(2, result[1].intValue());
		assertEquals(3, result[2].intValue());
	}

	@Test
	public void testGetValue_Boolean() {
		BooleanContainer container = new BooleanContainer(true);
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals(true, ((Boolean) result).booleanValue());
	}

	@Test
	public void testGetValue_Byte() {
		ByteContainer container = new ByteContainer((byte) 1);
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals((byte) 1, ((Byte) result).byteValue());
	}

	@Test
	public void testGetValue_Character() {
		CharacterContainer container = new CharacterContainer('z');
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals('z', ((Character) result).charValue());
	}

	@Test
	public void testGetValue_Double() {
		DoubleContainer container = new DoubleContainer(1.23d);
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals(1.23d, ((Double) result).doubleValue(), 0);
	}

	@Test
	public void testGetValue_Float() {
		FloatContainer container = new FloatContainer(1.23f);
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals(1.23f, ((Float) result).floatValue(), 0);
	}

	@Test
	public void testGetValue_Integer() {
		IntegerContainer container = new IntegerContainer(456);
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals(456, ((Integer) result).intValue());
	}

	@Test
	public void testGetValue_Long() {
		LongContainer container = new LongContainer(789l);
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals(789l, ((Long) result).longValue());
	}

	@Test
	public void testGetValue_Short() {
		ShortContainer container = new ShortContainer((short) 123);
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals((short) 123, ((Short) result).shortValue());
	}

	@Test
	public void testGetValue_String() {
		StringContainer container = new StringContainer("xyz");
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals("xyz", (String) result);
	}

	@Test
	public void testGetValue_Locale() {
		LocaleContainer container = new LocaleContainer(new Locale("en_US"));
		Object result = PrimitiveWrapperUtil.getValue(container);
		assertEquals(new Locale("en_US"), (Locale) result);
	}

	@Test
	public void testGetValue_JavaBean() {
		TestBean bean = new TestBean();
		Object result = PrimitiveWrapperUtil.getValue(bean);
		assertSame(bean, result);
	}

	private static class TestBean {
	}

}
