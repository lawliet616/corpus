package hu.bugbusters.corpus.core.vaadin.view;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

public class LoginView extends CustomComponent implements View, Button.ClickListener {
	public static final String NAME = "LoginView";
	private final TextField userName;
	private final PasswordField password;
	private final Button button;
	
	public LoginView() {
		VerticalLayout verticalLayout = new VerticalLayout();
		userName = new TextField("Felhasználónév");
		password = new PasswordField("Jelszó");
		button = new Button("Bejelentkezés", this);
		
		userName.setRequired(true);
		userName.setValue("");
		
		password.setRequired(true);
		password.setValue("");
		
		verticalLayout.addComponent(userName);
		verticalLayout.addComponent(password);
		verticalLayout.addComponent(button);
		verticalLayout.setMargin(true);
		
		setCompositionRoot(verticalLayout);
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
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userId", registeredUser.getId());
				this.getUI().getNavigator().navigateTo(UserView.NAME);
			} else {
				
			}
		} else {
			
		}
	}

}
