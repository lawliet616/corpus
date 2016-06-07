package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.Objects;
import java.util.Set;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.util.StringUtils;

@SuppressWarnings("serial")
public class emailTextView extends emailTextDesign implements View {
	private static final String STYLE_SEEN = "seen";
	private static final String STYLE_NOT_SEEN = "notSeen";
	private Dao dao = DaoImpl.getInstance();

	public emailTextView(final Message message, char seen) {
		if (seen == 'N') {
			seenButton.setStyleName(STYLE_NOT_SEEN);
		} else {
			seenButton.setStyleName(STYLE_SEEN);
			seenButton.setEnabled(false);
		}

		try {
			senderNameLabel.setValue(dao.getUserById(message.getCreatorId()).getFullName());
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
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
