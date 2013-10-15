package jp.sf.amateras.cookiesession.wrapper;

import java.io.PrintWriter;

public class PrintWriterWrapper extends PrintWriter {

	private CookieSessionResponse response;

	public PrintWriterWrapper(PrintWriter writer, CookieSessionResponse response){
		super(writer);
		this.response = response;
	}

	@Override
	public void flush() {
		if(!this.response.isCookieWritten()){
			this.response.writeSessionCookie();
		}
		super.flush();
	}

}
