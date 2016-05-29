package hu.bugbusters.corpus.core.vaadin.view.admin.userlist;

import org.junit.internal.runners.ErrorReportingRunner;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

public class UserListView extends UserListDesign implements View {
	public static final String NAME = "UserList";
	private BeanContainer<Long, RegisteredUser> userDataSource;
	private Dao dao;

	public UserListView() {
		dao = new DaoImpl();
		fillUserTable();
	}

	private void fillUserTable() {
		userDataSource = new BeanContainer<Long, RegisteredUser>(RegisteredUser.class);
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(dao.listAllUsers());
		registeredUserTable.setContainerDataSource(userDataSource);
		registeredUserTable.setVisibleColumns("username", "role", "fullname", "email");
		registeredUserTable.setColumnHeader("username", "Felhasználónév");
		registeredUserTable.setColumnHeader("role", "Jogosultság");
		registeredUserTable.setColumnHeader("fullname", "Név");
		registeredUserTable.setColumnHeader("email", "E-mail cím");
		
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
