package hu.bugbusters.corpus.core.vaadin.view.common.email;

import java.util.Set;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;
import hu.bugbusters.corpus.core.vaadin.view.admin.homepage.AdminHomepageView;
import hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews.SentEmailsView;
import hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews.inboxEmailView;
import hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews.newMailView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails.ChangePasswordView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails.ChangeSelfDetailsView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails.SelfDetailsView;
import hu.bugbusters.corpus.core.vaadin.view.teacher.courselist.TeacherCourseListView;
import hu.bugbusters.corpus.core.vaadin.view.teacher.studentlist.TeacherStudentListView;

@SuppressWarnings("serial")
public class emailView extends emailDesign implements View{
	public static final String NAME = "Email";
	
	private Set<Inbox> reveivedMails = Login.getLoggedInUser().getReceivedMails();
	
	
	public emailView() {
		
		emailSettings();
		
		buttonsettings();
		
		newMailSetting();
	}
	private void buttonsettings() {
			inboxButton.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					((CorpusUI) getUI()).navigate(inboxEmailView.NAME);
				}
			});
		
		sentEmailsButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(SentEmailsView.NAME);
			}
		});
		
	}
	public emailView(String name) {
		
		emailSettings();
		newMailSetting();
		buttonsettings();
		
		if(name.equals(inboxEmailView.NAME)){
			changeContet(new inboxEmailView());
		}else if(name.equals(SentEmailsView.NAME)){
			changeContet(new SentEmailsView());
		}
	}
	
	private void emailSettings() {
		
		int newMsg = 0;
		
		for (Inbox msg : reveivedMails) {
			if(msg.getSeen() == 'N'){
				newMsg++;
			}
		}
		
		String inboxText = "Beérkezett üzenetek &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class=”valo-menu-badge”>" + newMsg +"</span>";
		
		inboxButton.setCaption(inboxText);
		
	}
	
	private void newMailSetting() {
		newEmailButton.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						Window newMail = new Window("Új e-mail");
						newMail.setContent(new newMailView(newMail));
						newMail.setStyleName("subWindow");
						
						newMail.setResizable(false);
						newMail.setWidth("75%");
						newMail.setHeight("70%");
						newMail.center();
						newMail.setDraggable(false);
						newMail.setModal(true);
						
						((CorpusUI) getUI()).addWindow(newMail);	
				}
		});
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {	
		
	}
	
	protected void changeContet(Component component) {
		content.removeAllComponents();
		content.addComponent(component);
		content.setComponentAlignment(component, Alignment.MIDDLE_CENTER);

	}

}
