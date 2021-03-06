package hu.bugbusters.corpus.core.vaadin.view.admin;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;
import hu.bugbusters.corpus.core.vaadin.view.LogoutClickListener;
import hu.bugbusters.corpus.core.vaadin.view.admin.courselist.AdminCourseListView;
import hu.bugbusters.corpus.core.vaadin.view.admin.homepage.AdminHomepageView;
import hu.bugbusters.corpus.core.vaadin.view.admin.newcourse.NewCourseView;
import hu.bugbusters.corpus.core.vaadin.view.admin.newuser.NewUserView;
import hu.bugbusters.corpus.core.vaadin.view.admin.settings.SettingsView;
import hu.bugbusters.corpus.core.vaadin.view.admin.userlist.UserListView;
import hu.bugbusters.corpus.core.vaadin.view.common.email.EmailView;
import hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews.InboxEmailView;
import hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews.SentEmailsView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails.ChangePasswordView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails.ChangeSelfDetailsView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails.SelfDetailsView;

@SuppressWarnings("serial")
public class AdminView extends AdminDesign implements CorpusView {
	public static final String NAME = "Admin";
	public static final Role ROLE = Role.ADMIN;

	public AdminView() {
		logoutButton.addClickListener(new LogoutClickListener());
		userDetailsButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(SelfDetailsView.NAME);
			}
		});
		userListButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(UserListView.NAME);
			}
		});
		newUserButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(NewUserView.NAME);
			}
		});
		settingsButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(SettingsView.NAME);
			}
		});
		homepageSButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(AdminHomepageView.Name);
			}
		});
		courseListButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(AdminCourseListView.NAME);
			}
		});
		newCourseButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(NewCourseView.NAME);
			}
		});
		emailButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(EmailView.NAME);
			}
		});
	}

	protected void changeContet(Component component) {
		layout.removeAllComponents();
		layout.addComponent(component);
		layout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);

	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		Component component = null;
		String subView = viewChangeEvent.getParameters();
		if (subView.equals(SelfDetailsView.NAME)) {
			component = new SelfDetailsView();
		} else if (subView.equals(ChangeSelfDetailsView.NAME)) {
			component = new ChangeSelfDetailsView();
		} else if (subView.equals(ChangePasswordView.NAME)) {
			component = new ChangePasswordView();
		} else if (subView.equals(UserListView.NAME)) {
			component = new UserListView();
		} else if (subView.equals(NewUserView.NAME)) {
			component = new NewUserView();
		} else if (subView.equals(SettingsView.NAME)) {
			component = new SettingsView();
		} else if (subView.equals(AdminCourseListView.NAME)) {
			component = new AdminCourseListView();
		} else if (subView.equals(NewCourseView.NAME)) {
			component = new NewCourseView();
		} else if (subView.equals(EmailView.NAME)) {
			component = new EmailView();
		} else if (subView.equals(InboxEmailView.NAME)) {
			component = new EmailView(InboxEmailView.NAME);
		} else if (subView.equals(SentEmailsView.NAME)) {
			component = new EmailView(SentEmailsView.NAME);
		} else {
			component = new AdminHomepageView();
		}

		if (component != null) {
			changeContet(component);
		}
	}

	@Override
	public Role getRole() {
		return ROLE;
	}
}
