package hu.bugbusters.corpus.core.vaadin.view.login;

import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.util.Login;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

public class LoginView extends LoginDesign implements View, ClickListener{
	public static final String NAME = "Login";
	
	public LoginView() {

	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.userName.focus();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Dao dao = new DaoImpl();
		
		List<RegisteredUser> registeredUserList = dao.getUserByUserName(userName.getValue());
		
		if(!registeredUserList.isEmpty()) {
			RegisteredUser registeredUser = registeredUserList.get(0);
			
			if(registeredUser.getPassword().equals(password.getValue())) {
				Login.setLoggedInUserId(registeredUser.getId());
				
				((CorpusUI)getUI()).navigateToViewByRole(registeredUser.getRole());
			} else {
				//TODO: finish error handling
				throw new NotYetImplementedException("error handling missing here");
			}
		} else {
			//TODO: finish error handling
			throw new NotYetImplementedException("error handling missing here");
		}
	}


}
