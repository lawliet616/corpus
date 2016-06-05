package hu.bugbusters.corpus.core.vaadin.view.teacher;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;
import hu.bugbusters.corpus.core.vaadin.view.LogoutClickListener;
import hu.bugbusters.corpus.core.vaadin.view.admin.homepage.AdminHomepageView;
import hu.bugbusters.corpus.core.vaadin.view.admin.newuser.NewUserView;
import hu.bugbusters.corpus.core.vaadin.view.admin.settings.SettingsView;
import hu.bugbusters.corpus.core.vaadin.view.admin.userlist.UserListView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails.ChangePasswordView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails.ChangeSelfDetailsView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails.SelfDetailsView;
import hu.bugbusters.corpus.core.vaadin.view.teacher.studentlist.TeacherStudentListView;

@SuppressWarnings("serial")
public class TeacherView extends TeacherDesign implements CorpusView{
	public static final String NAME = "Teacher";
	public static final Role ROLE   = Role.TEACHER;

	public TeacherView(){
		logoutButton.addClickListener(new LogoutClickListener());
		userDetailsButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(SelfDetailsView.NAME);
			}
		});
		studentListButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(TeacherStudentListView.NAME);
			}
		});
		settingsButton2.addClickListener(new ClickListener() {

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
	}
	
	protected void changeContet(Component component) {
		content.removeAllComponents();
		content.addComponent(component);
		content.setComponentAlignment(component, Alignment.TOP_LEFT);

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
		} else if (subView.equals(TeacherStudentListView.NAME)) {
			component = new TeacherStudentListView();
		} else if (subView.equals(SettingsView.NAME)) {
			component = new SettingsView();
		}else{
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
