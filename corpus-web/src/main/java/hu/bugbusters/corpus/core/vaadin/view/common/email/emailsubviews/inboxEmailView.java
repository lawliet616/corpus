package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class inboxEmailView extends inboxEmailDesign implements View {
	public static final String NAME = "Inbox";
	
	public inboxEmailView() {
		comboBoxFill();
		fillWithEmails();
	}

	private void comboBoxFill() {
		
		
		
		cmbBoxSort.addItem("Név");
		cmbBoxSort.addItem("Év");
		
		cmbBoxSort.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				if(cmbBoxSort.getValue().equals("Név")){
					System.out.println("Siker");
				}
			}
		});
		
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
