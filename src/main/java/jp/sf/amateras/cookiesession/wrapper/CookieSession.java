package jp.sf.amateras.cookiesession.wrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionContext;

import jp.sf.amateras.cookiesession.CookieSessionConfig;

@SuppressWarnings("deprecation")
public class CookieSession implements HttpSession {

	public static final String CREATION_TIME = CookieSession.class.getName() + ".creationTime";
	public static final String LAST_ACCESSED_TIME = CookieSession.class.getName() + ".lastAccessedTime";
	public static final String MAX_INACTIVE_INTERVAL = CookieSession.class.getName() + ".maxInactiveInterval";

	private CookieSessionConfig config;

	private boolean isNew;

	private Map<String, Object> attributes;

	private ServletContext context;

	private boolean invalidate = false;

	private HttpServletRequest request;

	public CookieSession(CookieSessionConfig config, HttpServletRequest request,
			ServletContext context, Map<String, Object> attributes, boolean isNew){
		this.config = config;
		this.request = request;
		this.context = context;
		this.attributes = attributes;
		this.isNew = isNew;

		long currentTime = System.currentTimeMillis();
		if(isNew){
			setAttribute(CREATION_TIME, currentTime);
		}
		setAttribute(LAST_ACCESSED_TIME, currentTime);
	}

	private void checkAvailable(){
		if(invalidate){
			throw new IllegalStateException();
		}
	}

	public long getCreationTime() {
		checkAvailable();
		return (Long) getAttribute(CREATION_TIME);
	}

	public String getId() {
		checkAvailable();
		return request.getRemoteAddr();
	}

	public long getLastAccessedTime() {
		checkAvailable();
		return (Long) getAttribute(LAST_ACCESSED_TIME);
	}

	public ServletContext getServletContext() {
		checkAvailable();
		return context;
	}

	public void setMaxInactiveInterval(int interval) {
		checkAvailable();
		setAttribute(MAX_INACTIVE_INTERVAL, interval);
	}

	public int getMaxInactiveInterval() {
		checkAvailable();
		Integer interval = (Integer) getAttribute(MAX_INACTIVE_INTERVAL);
		if(interval == null){
			return 0;
		} else {
			return interval;
		}
	}

	/**
	 * This method throws UnsupportedOperationException.
	 */
	public HttpSessionContext getSessionContext() {
		checkAvailable();
		throw new UnsupportedOperationException();
	}

	public Object getAttribute(String name) {
		checkAvailable();
		return attributes.get(name);
	}

	public Object getValue(String name) {
		checkAvailable();
		return getAttribute(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		checkAvailable();
		return Collections.enumeration(attributes.keySet());
	}

	public String[] getValueNames() {
		checkAvailable();
		Set<String> keySet = attributes.keySet();
		return keySet.toArray(new String[keySet.size()]);
	}

	public void setAttribute(String name, Object value) {
		checkAvailable();
		boolean update = attributes.containsKey(name);
		attributes.put(name, value);

		// fire event
		for(HttpSessionAttributeListener listener: config.listeners){
			if(update){
				HttpSessionBindingEvent event = new HttpSessionBindingEvent(this, name, value); // TODO old value??
				listener.attributeReplaced(event);
			} else {
				HttpSessionBindingEvent event = new HttpSessionBindingEvent(this, name, value);
				listener.attributeAdded(event);
			}
		}
	}

	public void putValue(String name, Object value) {
		checkAvailable();
		setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		checkAvailable();
		if(attributes.containsKey(name)){
			Object value = attributes.remove(name);

			// fire event
			for(HttpSessionAttributeListener listener: config.listeners){
				HttpSessionBindingEvent event = new HttpSessionBindingEvent(this, name, value);
				listener.attributeRemoved(event);
			}
		}
	}

	public void removeValue(String name) {
		checkAvailable();
		removeAttribute(name);
	}

	public void invalidate() {
		checkAvailable();
		invalidate = true;
	}

	public boolean isNew() {
		checkAvailable();
		return isNew;
	}

	public boolean isInvalidated(){
		return invalidate;
	}

	public Map<String, Object> getAttributes(){
		return attributes;
	}

}
