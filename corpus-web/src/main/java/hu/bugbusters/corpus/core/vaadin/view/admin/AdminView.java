package hu.bugbusters.corpus.core.vaadin.view.admin;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;
import hu.bugbusters.corpus.core.vaadin.view.LogoutClickListener;
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
	}

	protected void changeContet(Component component) {
		layout.removeAllComponents();
		layout.addComponent(component);
		layout.setComponentAlignment(component, Alignment.TOP_LEFT);

	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		Component component = null;
		String subView = viewChangeEvent.getParameters();
		if (subView.equals(SelfDetailsView.NAME)) {
			component = new SelfDetailsView();
		}else if(subView.equals(ChangeSelfDetailsView.NAME)){
			component = new ChangeSelfDetailsView();
		}else if(subView.equals(ChangePasswordView.NAME)){
			component = new ChangePasswordView();
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
