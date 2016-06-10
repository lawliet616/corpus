package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Window;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

@SuppressWarnings("serial")
public class EmailTargetWindowView extends EmailTargetWindowDesign implements View {
	
	private Dao dao = DaoImpl.getInstance();
	private List<RegisteredUser> users = new ArrayList<>();
	private List<RegisteredUser> availableUsers = new ArrayList<>();
	private List<RegisteredUser> selectedUsers = new ArrayList<>();
	private List<Long> selectedUserId = new ArrayList<>();
	private BeanContainer<Long, RegisteredUser> userDataSource = new BeanContainer<Long, RegisteredUser>(RegisteredUser.class);
	
	public EmailTargetWindowView(final Window newMail, TextField targetTxt) {
		
		buttonSettings(newMail, targetTxt);
		listSettings();
		fillList();
	}

	private void buttonSettings(final Window newMail, final TextField targetTxt) {
		cancelButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				newMail.close();
				
			}
		});
		
		addButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				String targets = "";
				
				selectedUserId.addAll((Collection<Long>) targetList.getValue());
				
				for (RegisteredUser user : users) {
					for (Long id : selectedUserId) {
						if(user.getId() == id){
							selectedUsers.add(user);
							break;
						}
					}
				}
				
				for (RegisteredUser user : selectedUsers) {
					targets += user.getEmail() + ";";
				}
				
				targetTxt.setValue(targets);
				
				newMail.close();
				
			}
		});
		
	}

	private void listSettings() {

		targetList.setMultiSelect(true);
		targetList.setNullSelectionAllowed(false);
		targetList.setLeftColumnCaption("Felhszanálók");
		targetList.setRightColumnCaption("Kiválasztottak");
		
		
	}

	private void fillList() {
		
		users = dao.listAllUsers();
		
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(users);
		targetList.setContainerDataSource(userDataSource);
		targetList.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		targetList.setItemCaptionPropertyId("fullName");

		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
