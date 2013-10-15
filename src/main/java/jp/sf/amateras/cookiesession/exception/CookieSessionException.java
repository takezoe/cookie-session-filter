package jp.sf.amateras.cookiesession.exception;

public class CookieSessionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CookieSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public CookieSessionException(String message) {
		super(message);
	}

	public CookieSessionException(Throwable cause) {
		super(cause);
	}

}
