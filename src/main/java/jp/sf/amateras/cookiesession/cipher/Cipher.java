package jp.sf.amateras.cookiesession.cipher;

import javax.servlet.FilterConfig;

import jp.sf.amateras.cookiesession.CookieSessionFilter;
import jp.sf.amateras.cookiesession.exception.ChiperException;
import jp.sf.amateras.cookiesession.exception.InitializationException;

/**
 * An interface for cipher which encodes and decodes session cookie values.
 *
 * @author Naoki Takezoe
 */
public interface Cipher {

	/**
	 * Initializes this chiper.
	 *
	 * @param config the FilterConfig of {@link CookieSessionFilter}
	 * @throws InitializationException when initialization failed.
	 */
	public void init(FilterConfig config) throws InitializationException;

	/**
	 * Encodes the session cookie value.
	 *
	 * @param value the session cookie value
	 * @return the encoded string
	 * @throws ChiperException when encryption failed.
	 */
	public String encrypt(String value) throws ChiperException;

	/**
	 * Decodes the session cookie value.
	 *
	 * @param value the encoded string
	 * @return the decoded string
	 * @exception ChiperException when decryption failed.
	 */
	public String decrypt(String value) throws ChiperException;

}
