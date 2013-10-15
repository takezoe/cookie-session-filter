package jp.sf.amateras.cookiesession.cipher;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.FilterConfig;

import jp.sf.amateras.cookiesession.cipher.BlowfishCipher;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BlowfishCipherTest {

	@Mock
	private FilterConfig config;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(config.getInitParameter("key")).thenReturn("1234");
	}

	@Test
	public void testEncryptAndDecrypt() {
		BlowfishCipher chiper = new BlowfishCipher();
		chiper.init(config);

		String encrypted = chiper.encrypt("Hello World!!");
		assertEquals("Hello World!!", chiper.decrypt(encrypted));
	}

}
