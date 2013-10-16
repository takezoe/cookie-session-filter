package jp.sf.amateras.cookiesession;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionAttributeListener;

import jp.sf.amateras.cookiesession.cipher.Cipher;
import jp.sf.amateras.cookiesession.encoder.SessionEncoder;
import jp.sf.amateras.cookiesession.exception.InitializationException;
import jp.sf.amateras.cookiesession.util.StringUtil;
import jp.sf.amateras.cookiesession.wrapper.CookieSessionRequest;
import jp.sf.amateras.cookiesession.wrapper.CookieSessionResponse;

/**
 *
 * @author Naoki Takezoe
 */
public class CookieSessionFilter implements Filter {

	private CookieSessionConfig config;

	private ServletContext context;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.context = filterConfig.getServletContext();
		this.config = new CookieSessionConfig();

		// cookieName
		String cookieName = filterConfig.getInitParameter(CookieSessionConfig.CONFIG_COOKIE_NAME);
		if(StringUtil.isNotEmpty(cookieName)){
			this.config.cookieName = cookieName;
		}

		// encoder
		String encoderClassName = filterConfig.getInitParameter(CookieSessionConfig.CONFIG_ENCODER);
		if(StringUtil.isNotEmpty(encoderClassName)){
			try {
				@SuppressWarnings("unchecked")
				Class<SessionEncoder> clazz = (Class<SessionEncoder>) Class.forName(encoderClassName);
				this.config.encoder = clazz.newInstance();
			} catch(Exception ex){
				throw new InitializationException(ex);
			}
		}

		// chiper
		String cipherClassName = filterConfig.getInitParameter(CookieSessionConfig.CONFIG_CIPHER);
		if(StringUtil.isEmpty(cipherClassName)){
			throw new InitializationException("cipher has not been specified.");
		}
		try {
			@SuppressWarnings("unchecked")
			Class<Cipher> clazz = (Class<Cipher>) Class.forName(cipherClassName);
			this.config.cipher = clazz.newInstance();
		} catch(Exception ex){
			throw new InitializationException(ex);
		}
		this.config.cipher.init(filterConfig);

//		// maxCookie
//		String maxCookie = filterConfig.getInitParameter(CookieSessionConfig.CONFIG_MAX_COOKIE);
//		if(StringUtil.isNotEmpty(maxCookie)){
//			try {
//				this.config.maxCookie = Integer.parseInt(maxCookie.trim());
//			} catch(Exception ex){
//				throw new InitializationException("maxCookie has not been a number.");
//			}
//		}

		// cookieSize
		String cookieSize = filterConfig.getInitParameter(CookieSessionConfig.CONFIG_COOKIE_SIZE);
		if(StringUtil.isNotEmpty(cookieSize)){
			try {
				this.config.cookieSize = Integer.parseInt(cookieSize.trim());
			} catch(Exception ex){
				throw new InitializationException("cookieSize has not been a number.");
			}
		}

		// listeners
		String lieteners = filterConfig.getInitParameter(CookieSessionConfig.CONFIG_LISTENERS);
		if(StringUtil.isNotEmpty(lieteners)){
			for(String className: lieteners.split(",")){
				try {
					className = className.trim();
					HttpSessionAttributeListener listener = (HttpSessionAttributeListener) Class.forName(className).newInstance();
					this.config.listeners.add(listener);
				} catch(Exception ex){
					throw new InitializationException(ex); // TODO error message?
				}
			}
		}

		// timeout
		String timeout = filterConfig.getInitParameter(CookieSessionConfig.CONFIG_TIMEOUT);
		if(StringUtil.isNotEmpty(timeout)){
			try {
				this.config.timeout = Integer.parseInt(timeout);
			} catch(Exception ex){
				throw new InitializationException("timeout has not been a number.");
			}
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if(!(request instanceof CookieSessionRequest)){
			request = new CookieSessionRequest(
					(HttpServletRequest) request, this.config, this.context);

			response = new CookieSessionResponse(
					(HttpServletResponse) response, (CookieSessionRequest) request, this.config);
		}

		try {
			chain.doFilter(request, response);

		} finally {
			if(!((CookieSessionResponse) response).isCookieWritten()){
				((CookieSessionResponse) response).writeSessionCookie();
			}
		}
	}

	public void destroy() {
	}

}
