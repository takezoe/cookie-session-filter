package jp.sf.amateras.cookiesession.encoder;

import java.util.Map;

import javax.servlet.FilterConfig;

import jp.sf.amateras.cookiesession.CookieSessionFilter;
import jp.sf.amateras.cookiesession.exception.EncoderException;
import jp.sf.amateras.cookiesession.exception.InitializationException;

/**
 * Provides an interface which encodes session attributes as the string and decodes it.
 *
 * @author Naoki Takezoe
 */
public interface SessionEncoder {

	/**
	 * Initializes this chiper.
	 *
	 * @param config the FilterConfig of {@link CookieSessionFilter}
	 * @throws InitializationException when initialization failed.
	 */
	public void init(FilterConfig config) throws InitializationException;

	/**
	 * Encodes session attrbutes to the string.
	 *
	 * @param attributes session attributes
	 * @return the encoded string
	 * @throws EncoderException
	 */
	public String encode(Map<String, Object> attributes) throws EncoderException;

	/**
	 * Decodes session attributes from the encoded string.
	 *
	 * @param value the encoded string
	 * @return decoded session attributes
	 * @throws EncoderException
	 */
	public Map<String, Object>  decode(String value) throws EncoderException;

}
