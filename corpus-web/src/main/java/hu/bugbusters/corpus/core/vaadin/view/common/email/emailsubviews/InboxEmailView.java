package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class InboxEmailView extends InboxEmailDesign implements View {
	public static final String NAME = "Inbox";
	private Dao dao = DaoImpl.getInstance();
	private List<Inbox> sortedListByName = new ArrayList<>();
	private List<Inbox> sortedListByDate = new ArrayList<>();
	
	public InboxEmailView() {
		sorting();
		fillWithEmails();
		comboBoxFill();
	}

	private void comboBoxFill() {
		
		
		
		cmbBoxSort.addItem("Név");
		cmbBoxSort.addItem("Év");
		cmbBoxSort.setValue("Év");
		cmbBoxSort.setNullSelectionAllowed(false);
		cmbBoxSort.setTextInputAllowed(false);
		
		cmbBoxSort.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				if(cmbBoxSort.getValue().equals("Név")){
					messageList.removeAllComponents();
					for (Inbox mail : sortedListByName) {
						messageList.addComponent(new EmailTextView(mail.getMessage(), mail.getSeen()));
					}
					
				}
				else{
					messageList.removeAllComponents();
					for (Inbox mail : sortedListByDate) {
						messageList.addComponent(new EmailTextView(mail.getMessage(), mail.getSeen()));
					}
				}
			}
		});
		
	}

	private void fillWithEmails() {
		
		for (Inbox email : sortedListByDate) {
			messageList.addComponent(new EmailTextView(email.getMessage(), email.getSeen()));
		}
		
	}
	
	private void sorting() {
		sortedListByName.addAll(Login.getLoggedInUser().getReceivedMails());
		Collections.sort(sortedListByName, new Comparator<Inbox>() {

			@Override
			public int compare(Inbox o1, Inbox o2) {
				return o1.getMessage().getSentBy().getFullName().compareTo
						(o2.getMessage().getSentBy().getFullName());
			}
			
		});
		
		sortedListByDate.addAll(Login.getLoggedInUser().getReceivedMails());
		Collections.sort(sortedListByDate, new Comparator<Inbox>() {

			@Override
			public int compare(Inbox o1, Inbox o2) {
					return o2.getMessage().getTime().compareTo(o1.getMessage().getTime());
			}
		});
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
