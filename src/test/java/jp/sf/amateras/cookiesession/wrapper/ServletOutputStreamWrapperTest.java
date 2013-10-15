package jp.sf.amateras.cookiesession.wrapper;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class ServletOutputStreamWrapperTest {

	@Mock
	private CookieSessionResponse response;

	@Mock
	private OutputStream out;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testWrite() throws IOException {
		ServletOutputStreamWrapper wrapper = new ServletOutputStreamWrapper(out, response);

		wrapper.write(1);
		wrapper.write(2);
		wrapper.write(3);

		InOrder inOrder = inOrder(out);
		inOrder.verify(out).write(1);
		inOrder.verify(out).write(2);
		inOrder.verify(out).write(3);
	}

	@Test
	public void testFlush_1() throws IOException {
		when(response.isCookieWritten()).thenReturn(false);

		ServletOutputStreamWrapper wrapper = new ServletOutputStreamWrapper(out, response);

		wrapper.flush();

		verify(response).writeSessionCookie();
		verify(out).flush();
	}

	@Test
	public void testFlush_2() throws IOException {
		when(response.isCookieWritten()).thenReturn(true);

		ServletOutputStreamWrapper wrapper = new ServletOutputStreamWrapper(out, response);

		wrapper.flush();

		verify(response, never()).writeSessionCookie();
		verify(out).flush();
	}
}
