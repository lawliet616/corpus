package hu.bugbusters.corpus.core.vaadin.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.util.Login;
import hu.bugbusters.corpus.core.util.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

public class TeacherView extends CustomComponent implements View {
	public static final String NAME = "Techer";
	
	private Dao dao;
	
	public TeacherView() {
		dao = new DaoImpl();
		
		Label label = new Label("Hello Teacher!");
		
		setCompositionRoot(label);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		RegisteredUser registeredUser = dao.getUserById(Login.getLoggedInUserId());
		
		if(registeredUser.getRole() != Role.TEACHER) {
			((CorpusUI)getUI()).navigateToViewByRole(registeredUser.getRole());
		}
	}

}
