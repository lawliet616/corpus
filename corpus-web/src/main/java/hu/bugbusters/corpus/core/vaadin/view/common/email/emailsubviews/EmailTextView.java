package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.util.StringUtils;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

@SuppressWarnings("serial")
public class EmailTextView extends EmailTextDesign implements View {
	private static final String STYLE_SEEN = "seen";
	private static final String STYLE_NOT_SEEN = "notSeen";
	private Dao dao = DaoImpl.getInstance();
	private List<Inbox> sendMailContacts = new ArrayList<>();
	private List<String> sendMailContactNames = new ArrayList<>();
	private String names = "";
	
	private String sender;

	public EmailTextView(final Message message, char seen) {
		if (seen == 'N') {
			seenButton.setStyleName(STYLE_NOT_SEEN);
		} else {
			seenButton.setStyleName(STYLE_SEEN);
			seenButton.setEnabled(false);
		}

		sender = message.getSentBy().getFullName();
		senderNameLabel.setValue(sender);

		messageLabel.setCaption(message.getSubject());
		messageLabel.setValue(StringUtils.createPreviewFromMessage(message.getMessage()));


		seenButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				seenSetting(message);
			}
		});
		
				
		LayoutClickListener openMessageListener = new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				createMessageWindow(sender, message.getSubject(), message.getMessage());
				seenSetting(message);
				
			}
		};
		
		textContent.addLayoutClickListener(openMessageListener);
		
	}
	
	private void seenSetting(Message message) {
		seenButton.setStyleName(STYLE_SEEN);
		seenButton.setEnabled(false);

		Set<Inbox> receivedMessages = Login.getLoggedInUser().getReceivedMails();

		for (Inbox receivedMessage : receivedMessages) {
			if (Objects.equals(receivedMessage.getMessage().getId(), message.getId())) {
				receivedMessage.setSeen('Y');
				dao.updateEntity(receivedMessage);
				break;
			}
		}
		
	}

	public EmailTextView(final Message message, char seen, String send) {
		if (seen == 'N') {
			seenButton.setStyleName(STYLE_NOT_SEEN);
		} else {
			seenButton.setStyleName(STYLE_SEEN);
			seenButton.setEnabled(false);
		}
			
			sendMailContacts.addAll(message.getReceivedBy());
			
			for (Inbox user : sendMailContacts) {
				sendMailContactNames.add(user.getRegisteredUser().getFullName());
			}
			
			Collections.sort(sendMailContactNames, new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			
			for (String name : sendMailContactNames) {
				names += name + "; ";
			}
			
			senderNameLabel.setValue(names);
			
			labelHeader.setValue("Címzett:");
        
		messageLabel.setCaption(message.getSubject());
		messageLabel.setValue(StringUtils.createPreviewFromMessage(message.getMessage()));


		seenButton.setEnabled(false);
		
		sender = names;
		
				
		LayoutClickListener openMessageListener = new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				createMessageWindow(sender, message.getSubject(), message.getMessage());
				
			}
		};
		
		textContent.addLayoutClickListener(openMessageListener);
	}

	private void createMessageWindow(String sender, String subject, String msg) {
		Window messageWindow = new Window(sender);
		messageWindow.setContent(new EmailReadView(subject, msg, messageWindow));
		
		messageWindow.setResizable(false);
		messageWindow.setWidth("60%");
		messageWindow.setHeight("55%");
		messageWindow.center();
		messageWindow.setDraggable(false);
		messageWindow.setModal(true);
		
		((CorpusUI) getUI()).addWindow(messageWindow);	
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
