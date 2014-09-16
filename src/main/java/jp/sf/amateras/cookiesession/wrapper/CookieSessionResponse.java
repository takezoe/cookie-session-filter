package jp.sf.amateras.cookiesession.wrapper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import jp.sf.amateras.cookiesession.CookieSessionConfig;
import jp.sf.amateras.cookiesession.exception.ChiperException;
import jp.sf.amateras.cookiesession.exception.CookieSessionException;
import jp.sf.amateras.cookiesession.exception.EncoderException;
import jp.sf.amateras.cookiesession.util.StringUtil;

public class CookieSessionResponse extends HttpServletResponseWrapper {

	private CookieSessionRequest request;

	private CookieSessionConfig config;

	private ServletOutputStreamWrapper out;

	private PrintWriterWrapper writer;

	private boolean isCookieWritten = false;

	public CookieSessionResponse(HttpServletResponse response, CookieSessionRequest request, CookieSessionConfig config) {
		super(response);
		this.request = request;
		this.config = config;
	}

	@Override
	public void flushBuffer() throws IOException {
		if(!this.isCookieWritten){
			writeSessionCookie();
		}
		super.flushBuffer();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if(this.out == null){
			this.out = new ServletOutputStreamWrapper(super.getOutputStream(), this);
		}
		return this.out;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if(this.writer == null){
			this.writer = new PrintWriterWrapper(super.getWriter(), this);
		}
		return this.writer;
	}

	public void writeSessionCookie(){
		this.isCookieWritten = true;

		// Updates SessionCookie
		CookieSession session = (CookieSession) this.request.getSession();
		if(session == null || session.isInvalidated()){
			Cookie cookie = new Cookie(this.config.cookieName, "");
			if(StringUtil.isNotEmpty(this.config.cookiePath)) {
				cookie.setPath(this.config.cookiePath);
			}
			if(StringUtil.isNotEmpty(this.config.cookieDomain)) {
				cookie.setDomain(this.config.cookieDomain);
			}
			cookie.setMaxAge(0);
			addCookie(cookie);
		} else {
			// Write SessionCookie
			try {
				String encodedValue = this.config.encoder.encode(session.getAttributes());
				String encryptedValue = this.config.cipher.encrypt(encodedValue);
				if(encryptedValue.length() > this.config.cookieSize){
					throw new CookieSessionException("Cookie size exceeds limit.");
				}
				Cookie cookie = new Cookie(this.config.cookieName, encryptedValue);
				if(StringUtil.isNotEmpty(this.config.cookiePath)) {
					cookie.setPath(this.config.cookiePath);
				}
				if(StringUtil.isNotEmpty(this.config.cookieDomain)) {
					cookie.setDomain(this.config.cookieDomain);
				}
				addCookie(cookie);
				
			} catch(ChiperException ex){
				// TODO warn log??
				ex.printStackTrace();
			} catch(EncoderException ex){
				// TODO warn log??
				ex.printStackTrace();
			}
		}
	}

	public boolean isCookieWritten(){
		return this.isCookieWritten;
	}

}
