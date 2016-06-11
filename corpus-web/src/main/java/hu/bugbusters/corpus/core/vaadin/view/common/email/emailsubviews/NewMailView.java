package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

import hu.bugbusters.corpus.core.bean.TakenCourse;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.mail.Mail;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

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
				
				String[] targetString = targetTxt.getValue().split(";");
				
				List<String> targets = Arrays.asList(targetString);
				
				try {
					for (String t : targets) {
						System.out.println(t);
					}
					
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
		
		addTargetButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Window newMail = new Window("Felhasználók");
				newMail.setContent(new EmailTargetWindowView(newMail, targetTxt));
				newMail.setStyleName("subWindow");
				
				newMail.setResizable(false);
				newMail.setWidth("40%");
				newMail.setHeight("70%");
				newMail.center();
				newMail.setDraggable(false);
				newMail.setModal(true);
				
				((CorpusUI) getUI()).addWindow(newMail);	
				
			}
		});
	}

	public NewMailView(final Window newMail, Set<TakenCourse> courseStudents) {
		
		addTargetButton.setEnabled(false);
		addTargetButton.setVisible(false);
		
		final List<String> targets = new ArrayList<>();
		
		String names = "";

		for (TakenCourse registeredUser : courseStudents) {
			if(registeredUser.getRegisteredUser().getRole() != Role.TEACHER){
				targets.add(registeredUser.getRegisteredUser().getEmail());
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
