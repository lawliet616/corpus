package hu.bugbusters.corpus.core.vaadin.view.admin;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;
import hu.bugbusters.corpus.core.vaadin.view.LogoutClickListener;
import hu.bugbusters.corpus.core.vaadin.view.common.SelfDetailsView;

@SuppressWarnings("serial")
public class AdminView extends AdminDesign implements CorpusView {
	public static final String NAME = "Admin";
	public static final Role ROLE   = Role.ADMIN;

	public AdminView(){
		logoutButton.addClickListener(new LogoutClickListener());
		userDetailsButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
					changeConntet(new SelfDetailsView());
				}
		});
	}

	protected void changeConntet(SelfDetailsView selfDetailsView) {
		layout.removeAllComponents();
		layout.addComponent(selfDetailsView);
		layout.setComponentAlignment(selfDetailsView, Alignment.TOP_LEFT);
		
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

	}

	@Override
	public Role getRole() {
		return ROLE;
	}
}

