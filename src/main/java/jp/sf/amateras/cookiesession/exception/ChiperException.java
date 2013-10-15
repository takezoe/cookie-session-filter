package jp.sf.amateras.cookiesession.exception;

public class ChiperException extends CookieSessionException {

	private static final long serialVersionUID = 1L;

	public ChiperException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChiperException(String message) {
		super(message);
	}

	public ChiperException(Throwable cause) {
		super(cause);
	}

}
