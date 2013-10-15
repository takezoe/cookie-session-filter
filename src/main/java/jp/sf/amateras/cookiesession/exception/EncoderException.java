package jp.sf.amateras.cookiesession.exception;

public class EncoderException extends CookieSessionException {

	private static final long serialVersionUID = 1L;

	public EncoderException(Throwable cause) {
		super(cause);
	}

	public EncoderException(String message, Throwable cause) {
		super(message, cause);
	}

	public EncoderException(String message) {
		super(message);
	}

}
