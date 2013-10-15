package jp.sf.amateras.cookiesession.exception;

public class InitializationException extends CookieSessionException {

	private static final long serialVersionUID = 1L;

	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializationException(String message) {
		super(message);
	}

	public InitializationException(Throwable cause) {
		super(cause);
	}

}
