package jp.sf.amateras.cookiesession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.sf.amateras.cookiesession.cipher.BlowfishCipher;
import jp.sf.amateras.cookiesession.encoder.BinaryEncoder;
import jp.sf.amateras.cookiesession.exception.CookieSessionException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class CookieSessionFilterTest {

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private FilterChain chain;

	@Mock
	private FilterConfig config;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(config.getInitParameter("cipher")).thenReturn(BlowfishCipher.class.getName());
		when(config.getInitParameter("key")).thenReturn("1234");
	}

	@Test
	public void testDoFilter_singleCookie() throws Exception {
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) throws Throwable {
				HttpServletRequest request = (HttpServletRequest) invocation.getArguments()[0];

				LoginInfo loginInfo = new LoginInfo();
				loginInfo.userId = "testuser";
				loginInfo.userName = "Test User";
				loginInfo.userType = 1;

				request.getSession().setAttribute("loginInfo", loginInfo);

				return null;
			}
		}).when(chain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));

		CookieSessionFilter filter = new CookieSessionFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);

		ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
		verify(response, times(1)).addCookie(captor.capture());

		List<Cookie> cookies = captor.getAllValues();
		assertEquals(1, cookies.size());
		assertEquals("session-cookie", cookies.get(0).getName());

		BlowfishCipher chiper = new BlowfishCipher();
		chiper.init(config);

		Map<String, Object> map = new BinaryEncoder().decode(chiper.decrypt(cookies.get(0).getValue()));
		LoginInfo result = (LoginInfo) map.get("loginInfo");

		assertEquals("testuser", result.userId);
		assertEquals("Test User", result.userName);
		assertEquals(1, result.userType);
	}

	@Test
	public void testDoFilter_exceedCookieSize() throws Exception {
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) throws Throwable {
				HttpServletRequest request = (HttpServletRequest) invocation.getArguments()[0];

				LoginInfo loginInfo = new LoginInfo();
				loginInfo.userId = "testuser";
				loginInfo.userName = "Test User";
				loginInfo.userType = 1;

				request.getSession().setAttribute("loginInfo", loginInfo);

				return null;
			}
		}).when(chain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));

		when(config.getInitParameter("cookieSize")).thenReturn("510");

		CookieSessionFilter filter = new CookieSessionFilter();
		filter.init(config);
		try {
			filter.doFilter(request, response, chain);
			fail();
		} catch(CookieSessionException ex){
			assertEquals("Cookie size exceeds limit.", ex.getMessage());
		}
	}

	@Test
	public void testDoFilter_removeCookie() throws Exception {
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) throws Throwable {
				HttpServletRequest request = (HttpServletRequest) invocation.getArguments()[0];

				LoginInfo loginInfo = new LoginInfo();
				loginInfo.userId = "testuser";
				loginInfo.userName = "Test User";
				loginInfo.userType = 1;

				request.getSession().setAttribute("loginInfo", loginInfo);

				return null;
			}
		}).when(chain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));

		when(request.getCookies()).thenReturn(new Cookie[]{
				new Cookie("session-cookie", "")
		});

		CookieSessionFilter filter = new CookieSessionFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);

		ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
		verify(response, times(1)).addCookie(captor.capture());

		List<Cookie> cookies = captor.getAllValues();
		assertEquals(1, cookies.size());

		assertEquals("session-cookie", cookies.get(0).getName());
		assertTrue(cookies.get(0).getValue().length() > 0);
		assertEquals(-1, cookies.get(0).getMaxAge());
	}

	@Test
	public void testDoFilter_invalidateSession() throws Exception {
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) throws Throwable {
				HttpServletRequest request = (HttpServletRequest) invocation.getArguments()[0];
				request.getSession().invalidate();
				return null;
			}
		}).when(chain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));

		when(request.getCookies()).thenReturn(new Cookie[]{
				new Cookie("session-cookie", "")
		});

		CookieSessionFilter filter = new CookieSessionFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);

		ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
		verify(response, times(1)).addCookie(captor.capture());

		List<Cookie> cookies = captor.getAllValues();
		assertEquals(1, cookies.size());
		assertEquals("session-cookie", cookies.get(0).getName());
		assertTrue(cookies.get(0).getValue().length() == 0);
		assertEquals(0, cookies.get(0).getMaxAge());
	}

	public static class LoginInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String userId;
		public String userName;
		public int userType;
	}

}
