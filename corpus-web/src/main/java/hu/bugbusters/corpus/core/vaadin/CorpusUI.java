package hu.bugbusters.corpus.core.vaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.vaadin.view.LoginView;
import hu.bugbusters.corpus.core.vaadin.view.UserView;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CorpusUI extends UI {
	
	@Override
	protected void init(final VaadinRequest request) {
		Navigator navigator = new Navigator(this, this);

		navigator.addView(LoginView.NAME, new LoginView());
		navigator.addView(UserView.NAME, new UserView());
		
		navigator.navigateTo(LoginView.NAME);
	}
}
