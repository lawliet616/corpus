package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;

import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class SentEmailsView extends SentEmailsDesign implements View {
	public static final String NAME = "SentMails";
	private Dao dao = DaoImpl.getInstance();
	private List<Message> sortedListByName = new ArrayList<>();
	private List<Message> sortedListByDate = new ArrayList<>();
	
	public SentEmailsView() {
		fillWithEmails();
		sorting();
		comboBoxFill();
	}
	
	private void comboBoxFill() {

		cmbBoxSort.addItem("Név");
		cmbBoxSort.addItem("Év");
		cmbBoxSort.setValue("Név");
		cmbBoxSort.setNullSelectionAllowed(false);
		cmbBoxSort.setTextInputAllowed(false);
		
		cmbBoxSort.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				if(cmbBoxSort.getValue().equals("Név")){
					messageList.removeAllComponents();
					for (Message mail : sortedListByName) {
						messageList.addComponent(new EmailTextView(mail, 'Y'));
					}
					
				}
				else{
					for (Message mail : sortedListByDate) {
						messageList.removeAllComponents();
						messageList.addComponent(new EmailTextView(mail, 'Y'));
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
		
		sortedListByDate.addAll(Login.getLoggedInUser().getSentMails());
		Collections.sort(sortedListByDate, new Comparator<Message>() {

			@Override
			public int compare(Message o1, Message o2) {
					return o1.getTime().compareTo(o2.getTime());
			}
		});
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
