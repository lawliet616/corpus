package hu.bugbusters.corpus.core.vaadin.view.teacher;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;

@SuppressWarnings("serial")
public class TeacherView extends CustomComponent implements CorpusView {
	public static final String NAME = "Teacher";
	public static final Role ROLE   = Role.TEACHER;
	
	private Dao dao;
	
	public TeacherView() {
		dao = new DaoImpl();
		
		Label label = new Label("Hello Teacher!");
		
		setCompositionRoot(label);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	@Override
	public Role getRole() {
		return ROLE;
	}

}
