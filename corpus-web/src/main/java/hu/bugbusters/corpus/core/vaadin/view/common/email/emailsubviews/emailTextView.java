package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.Set;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class emailTextView extends emailTextDesign implements View {
	
	private Dao dao = DaoImpl.getInstance();

	public emailTextView(final Message message, char seen) {
		if(seen == 'N'){
			seenButton.setStyleName("notSeen");
		}else{
			seenButton.setStyleName("seen");
			seenButton.setEnabled(false);
		}
		
		senderNameLabel.setValue("Anyánk");
		messageLabel.setCaption(message.getSubject());
		messageLabel.setValue(message.getMessage());
		
		seenButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				seenButton.setStyleName("seen");
				seenButton.setEnabled(false);
				
				Set<Inbox> receivedMessages = Login.getLoggedInUser().getReceivedMails();
				
				for (Inbox receivedMessage : receivedMessages){
					if(receivedMessage.getMessage().getId() == message.getId()){
						receivedMessage.setSeen('Y');
					}
				}
				
				dao.updateEntity(receivedMessages);
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}

}
