package jp.sf.amateras.cookiesession;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionAttributeListener;

import jp.sf.amateras.cookiesession.cipher.Cipher;
import jp.sf.amateras.cookiesession.encoder.BinaryEncoder;
import jp.sf.amateras.cookiesession.encoder.SessionEncoder;

public class CookieSessionConfig {

	public static final String CONFIG_COOKIE_NAME = "cookieName";

	public static final String CONFIG_CIPHER = "cipher";

	public static final String CONFIG_COOKIE_SIZE = "cookieSize";

	public static final String CONFIG_LISTENERS = "listeners";

	public static final String CONFIG_TIMEOUT = "timeout";

	public static final String CONFIG_ENCODER = "encoder";

	public String cookieName = "session-cookie";

	public Cipher cipher;

	public int cookieSize = 4096;

	public List<HttpSessionAttributeListener> listeners = new ArrayList<HttpSessionAttributeListener>();

	public int timeout = 0;

	public SessionEncoder encoder = new BinaryEncoder();

}
