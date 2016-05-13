package hu.bugbusters.corpus.core.vaadin.view;

import com.vaadin.navigator.View;

import hu.bugbusters.corpus.core.login.Role;

public interface CorpusView extends View {
	public Role getRole();
}
