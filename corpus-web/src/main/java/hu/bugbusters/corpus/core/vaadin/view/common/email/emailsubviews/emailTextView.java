package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.Objects;
import java.util.Set;

import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;

import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.util.StringUtils;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

@SuppressWarnings("serial")
public class emailTextView extends emailTextDesign implements View {
	private static final String STYLE_SEEN = "seen";
	private static final String STYLE_NOT_SEEN = "notSeen";
	private Dao dao = DaoImpl.getInstance();
	
	private String sender;

	public emailTextView(final Message message, char seen) {
		if (seen == 'N') {
			seenButton.setStyleName(STYLE_NOT_SEEN);
		} else {
			seenButton.setStyleName(STYLE_SEEN);
			seenButton.setEnabled(false);
		}

		try {
			sender = dao.getUserById(message.getCreatorId()).getFullName();
			senderNameLabel.setValue(sender);
		} catch (UserNotFoundException e) {
			senderNameLabel.setValue("NOT_FOUND");
		}
        
		messageLabel.setCaption(message.getSubject());
		messageLabel.setValue(StringUtils.createPreviewFromMessage(message.getMessage()));


		seenButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				seenButton.setStyleName(STYLE_SEEN);
				seenButton.setEnabled(false);

				Set<Inbox> receivedMessages = Login.getLoggedInUser().getReceivedMails();

				for (Inbox receivedMessage : receivedMessages) {
					if (Objects.equals(receivedMessage.getMessage().getId(), message.getId())) {
						receivedMessage.setSeen('Y');
					}
				}

				dao.updateEntity(receivedMessages);
			}
		});
		
				
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
