package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
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
				newMail.close();
			}
		});
	}

	public NewMailView(final Window newMail, Set<RegisteredUser> courseStudents) {
		
		final List<String> targets = new ArrayList<>();
		
		String names = "";

		for (RegisteredUser registeredUser : courseStudents) {
			if(registeredUser.getRole() != Role.TEACHER){
				targets.add(registeredUser.getEmail());
			}
		}
		
		for (String target : targets) {
			names += target + "; ";
		}
		
		targetTxt.setValue(names);
		targetTxt.setReadOnly(true);
		
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
					System.out.println(targets.size());
					Mail.sendMail(Login.getLoggedInUserId(), targets, subjectTxt.getValue(), messageTxt.getValue(),
							false);
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (UserNotFoundException e) {
					e.printStackTrace();
				}
				newMail.close();
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {	
	}

}
