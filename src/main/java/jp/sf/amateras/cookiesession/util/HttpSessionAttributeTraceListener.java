package jp.sf.amateras.cookiesession.util;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * The HttpServletAttributeListener which traces session attribute events.
 * <p>
 * Use this implementation for debug only.
 *
 * @author Naoki Takezoe
 */
public class HttpSessionAttributeTraceListener implements HttpSessionAttributeListener {

	public void attributeAdded(HttpSessionBindingEvent se) {
		System.out.println("attributeAdded: name=" + se.getName() + ", value=" + se.getValue());
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
		System.out.println("attributeRemoved: name=" + se.getName() + ", value=" + se.getValue());
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
		System.out.println("attributeReplaced: name=" + se.getName() + ", value=" + se.getValue());
	}

}
