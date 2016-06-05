package hu.bugbusters.corpus.core.vaadin.view.admin.userlist;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Role;

@SuppressWarnings("serial")
public class UserListView extends UserListDesign implements View {
	public static final String NAME = "UserList";
	private BeanContainer<Long, RegisteredUser> userDataSource;
	private Filter userFilter;
	private Filter adminFilter;
	private Filter teacherFilter;
	private Dao dao;

	public UserListView() {
		dao = new DaoImpl();
		createFilters();
		fillUserTable();
		createSelectGoup();
	}

	private void createSelectGoup() {
		for (Role role : Role.values()) {
			selectGroup.addItem(role);
		}
		selectGroup.addItem("Egyik sem");
		selectGroup.setValue("Egyik sem");
		selectGroup.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				selectFilter();
			}
		});

	}

	protected void selectFilter() {
		userDataSource.removeAllContainerFilters();
		if(selectGroup.getValue() == Role.ADMIN) {
			userDataSource.addContainerFilter(adminFilter);
		} else if(selectGroup.getValue() == Role.TEACHER) {
			userDataSource.addContainerFilter(teacherFilter);
		} else if(selectGroup.getValue() == Role.USER) {
			userDataSource.addContainerFilter(userFilter);
		}
	}

	private void createFilters() {
		userFilter = new SimpleStringFilter("role", Role.USER.toString(), true, false);
		adminFilter = new SimpleStringFilter("role", Role.ADMIN.toString(), true, false);
		teacherFilter = new SimpleStringFilter("role", Role.TEACHER.toString(), true, false);
	}

	private void fillUserTable() {
		userDataSource = new BeanContainer<Long, RegisteredUser>(RegisteredUser.class);
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(dao.listAllUsers());
		registeredUserTable.setContainerDataSource(userDataSource);
		registeredUserTable.setVisibleColumns("username", "role", "fullName", "email");
		registeredUserTable.setColumnHeader("username", "Felhasználónév");
		registeredUserTable.setColumnHeader("role", "Jogosultság");
		registeredUserTable.setColumnHeader("fullName", "Név");
		registeredUserTable.setColumnHeader("email", "E-mail cím");
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}
}
