package jp.sf.amateras.cookiesession.wrapper;

import static org.mockito.Mockito.*;

import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PrintWriterWrapperTest {

	@Mock
	private PrintWriter writer;

	@Mock
	private CookieSessionResponse response;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFlush_1() {
		when(response.isCookieWritten()).thenReturn(false);

		PrintWriterWrapper wrapper = new PrintWriterWrapper(writer, response);
		wrapper.flush();

		verify(response).writeSessionCookie();
	}

	@Test
	public void testFlush_2() {
		when(response.isCookieWritten()).thenReturn(true);

		PrintWriterWrapper wrapper = new PrintWriterWrapper(writer, response);
		wrapper.flush();

		verify(response, never()).writeSessionCookie();
	}
}
