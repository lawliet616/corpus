package hu.bugbusters.corpus.core.vaadin.view.admin.userlist;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;

@SuppressWarnings("serial")
public class UserListView extends UserListDesign implements View {
	public static final String NAME = "UserList";
	private BeanContainer<Long, RegisteredUser> userDataSource;
	private Filter userFilter;
	private Filter adminFilter;
	private Filter teacherFilter;
	private Dao dao = DaoImpl.getInstance();

	public UserListView() {
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
		if (selectGroup.getValue() == Role.ADMIN) {
			userDataSource.addContainerFilter(adminFilter);
		} else if (selectGroup.getValue() == Role.TEACHER) {
			userDataSource.addContainerFilter(teacherFilter);
		} else if (selectGroup.getValue() == Role.USER) {
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
		refillTable();
		registeredUserTable.setContainerDataSource(userDataSource);
		registeredUserTable.addGeneratedColumn("delete", new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				Button btnDelete = new Button("Töröl");
				btnDelete.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						BeanItem<RegisteredUser> selectedBean = (BeanItem<RegisteredUser>) source.getItem(itemId);
						deleteElement(selectedBean.getBean());
					}
				});

				return btnDelete;
			}
		});
		registeredUserTable.setVisibleColumns("username", "role", "fullName", "email", "delete");
		registeredUserTable.setColumnHeader("username", "Felhasználónév");
		registeredUserTable.setColumnHeader("role", "Jogosultság");
		registeredUserTable.setColumnHeader("fullName", "Név");
		registeredUserTable.setColumnHeader("email", "E-mail cím");
		registeredUserTable.setColumnHeader("delete", "Törlés");
	}

	private void refillTable() {
		userDataSource.removeAllItems();
		userDataSource.addAll(dao.listAllUsers());
	}

	private void deleteElement(RegisteredUser user) {
		if (user.getId() != Login.getLoggedInUserId()) {
			dao.deleteEntity(user);
			Notification.show("Sikeresen törölte a felhasználót!", Notification.Type.HUMANIZED_MESSAGE);
			refillTable();
		} else {
			Notification.show("Saját magadat nem törölheted ki.", Notification.Type.WARNING_MESSAGE);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}
}
