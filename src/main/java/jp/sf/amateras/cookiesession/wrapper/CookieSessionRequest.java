package jp.sf.amateras.cookiesession.wrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import jp.sf.amateras.cookiesession.CookieSessionConfig;
import jp.sf.amateras.cookiesession.exception.ChiperException;
import jp.sf.amateras.cookiesession.exception.EncoderException;
import jp.sf.amateras.cookiesession.primitive.PrimitiveWrapperUtil;
import jp.sf.amateras.cookiesession.util.StringUtil;

public class CookieSessionRequest extends HttpServletRequestWrapper {

	private CookieSession session;

	private CookieSessionConfig config;

	private ServletContext context;

	public CookieSessionRequest(HttpServletRequest request, CookieSessionConfig config,
			ServletContext context) {
		super(request);
		this.config = config;
		this.context = context;
	}

	@Override
	public HttpSession getSession(boolean create) {
		if(this.session != null){
			return this.session;
		}

		String cookieValue = getCookieValue();
		if(StringUtil.isEmpty(cookieValue)){
			this.session = createSession(new HashMap<String, Object>(), create, true);
			return this.session;
		}

		Map<String, Object> attributes = null;
		try {
			String decrypted = this.config.cipher.decrypt(cookieValue);
			attributes = this.config.encoder.decode(decrypted);

		} catch(ChiperException ex){
			// TODO warn log??
			this.session = createSession(new HashMap<String, Object>(), create, true);
			return this.session;

		} catch(EncoderException ex){
			// TODO warn log??
			this.session = createSession(new HashMap<String, Object>(), create, true);
			return this.session;
		}

		// check maxInactiveInterval
		// TODO maxInactiveInterval should be set as Cookie expiration time?
		if(attributes.get(CookieSession.MAX_INACTIVE_INTERVAL) != null){
			int interval = (Integer) PrimitiveWrapperUtil.getValue(attributes.get(CookieSession.MAX_INACTIVE_INTERVAL));
			if(interval > 0){
				long lastAccessedTime = (Long) PrimitiveWrapperUtil.getValue(attributes.get(CookieSession.LAST_ACCESSED_TIME));
				if(System.currentTimeMillis() - lastAccessedTime >= interval * 1000){
					this.session = createSession(new HashMap<String, Object>(), create, true);
					return this.session;
				}
			}
		}

		// check session timeout
		// TODO timeout should be set as Cookie expiration time?
		if(this.config.timeout > 0){
			long lastAccessedTime = (Long) PrimitiveWrapperUtil.getValue(attributes.get(CookieSession.LAST_ACCESSED_TIME));
			if(System.currentTimeMillis() - lastAccessedTime >= this.config.timeout * 60 * 1000){
				this.session = createSession(new HashMap<String, Object>(), create, true);
				return this.session;
			}
		}

		this.session = createSession(attributes, create, false);
		return this.session;
	}

	private CookieSession createSession(Map<String, Object> attributes, boolean create, boolean isNew){
		if(create == false){
			return null;
		}
		return new CookieSession(this.config, this, this.context, attributes, isNew);
	}

	private String getCookieValue(){
		Cookie cookie = getSessionCookie();
		if(cookie != null){
			return cookie.getValue();
		} else {
			return "";
		}
	}

	public Cookie getSessionCookie(){
		Cookie[] cookies = getCookies();
		if(cookies != null){
			for(Cookie cookie: cookies){
				if(cookie.getName().equals(this.config.cookieName)){
					return cookie;
				}
			}
		}
		return null;
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

}
