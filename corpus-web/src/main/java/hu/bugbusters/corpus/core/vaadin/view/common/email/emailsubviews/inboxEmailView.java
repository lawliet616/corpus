package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class inboxEmailView extends inboxEmailDesign implements View {
	public static final String NAME = "Inbox";
	
	public inboxEmailView() {
		fillWithEmails();
	}

	private void fillWithEmails() {
		
		for (Inbox email : Login.getLoggedInUser().getReceivedMails()) {
			messageList.addComponent(new emailTextView(email.getMessage(), email.getSeen()));
		}
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
