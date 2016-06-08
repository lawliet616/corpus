package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import javax.mail.MessagingException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.mail.Mail;

@SuppressWarnings("serial")
public class NewMailView extends NewMailDesign implements View {
	
	public NewMailView(final Window newMail) {
		
		cancelButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				newMail.close();
				
			}
		});
		
		sendButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				try {
					Mail.sendMail(Login.getLoggedInUserId(), targetTxt.getValue(), subjectTxt.getValue(), messageTxt.getValue(),
							false);
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (UserNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {	
	}

}
