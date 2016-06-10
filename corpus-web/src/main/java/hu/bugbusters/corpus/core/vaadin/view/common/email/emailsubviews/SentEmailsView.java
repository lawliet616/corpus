package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class SentEmailsView extends SentEmailsDesign implements View {
	public static final String NAME = "SentMails";
	private Dao dao = DaoImpl.getInstance();
	private List<Message> sortedListByName = new ArrayList<>();
	private String send = "";
	
	public SentEmailsView() {
		fillWithEmails();
		sorting();
		comboBoxFill();
	}
	
	private void comboBoxFill() {

		cmbBoxSort.addItem("Név");
		cmbBoxSort.setValue("Név");
		cmbBoxSort.setNullSelectionAllowed(false);
		cmbBoxSort.setTextInputAllowed(false);
		
		cmbBoxSort.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				if(cmbBoxSort.getValue().equals("Név")){
					messageList.removeAllComponents();
					for (Message mail : sortedListByName) {
						messageList.addComponent(new EmailTextView(mail, 'Y', send));
					}
					
				}
			}
		});
		
	}

	private void fillWithEmails() {
		
		String send = "";
		
		for (Message msg : Login.getLoggedInUser().getSentMails()) {
			messageList.addComponent(new EmailTextView(msg, 'Y', send));
		}
		
	}
	
	private void sorting() {
		sortedListByName.addAll(Login.getLoggedInUser().getSentMails());
		Collections.sort(sortedListByName, new Comparator<Message>() {

			@Override
			public int compare(Message o1, Message o2) {
				return o1.getSentBy().getFullName().compareTo
							(o2.getSentBy().getFullName());
			}
			
		});
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
