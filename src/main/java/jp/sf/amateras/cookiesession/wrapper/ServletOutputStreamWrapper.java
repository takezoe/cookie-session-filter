package jp.sf.amateras.cookiesession.wrapper;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public class ServletOutputStreamWrapper extends ServletOutputStream {

	private OutputStream out;

	private CookieSessionResponse response;

	public ServletOutputStreamWrapper(OutputStream out, CookieSessionResponse response){
		this.out = out;
		this.response = response;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void flush() throws IOException {
		if(!this.response.isCookieWritten()){
			this.response.writeSessionCookie();
		}
		out.flush();
	}

}
