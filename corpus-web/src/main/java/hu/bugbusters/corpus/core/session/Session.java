package hu.bugbusters.corpus.core.session;

import com.vaadin.server.VaadinService;
import com.vaadin.server.WrappedSession;

public class Session {
	public static void setAttribute(String name, Object value) {
		getCurrentWrappedSession().setAttribute(name, value);
	}

	public static Object getAttribute(String name) {
		return getCurrentWrappedSession().getAttribute(name);
	}

	public static void invalidate() {
		getCurrentWrappedSession().invalidate();
	}

	public static WrappedSession getCurrentWrappedSession() {
		return VaadinService.getCurrentRequest().getWrappedSession();
	}
}
