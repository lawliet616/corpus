package hu.bugbusters.corpus.core.vaadin.view.common.email;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;

@SuppressWarnings("serial")
public class emailView extends emailDesign implements CorpusView{
	public static final String NAME = "Email";

	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	@Override
	public Role getRole() {
		return null;
	}

}
