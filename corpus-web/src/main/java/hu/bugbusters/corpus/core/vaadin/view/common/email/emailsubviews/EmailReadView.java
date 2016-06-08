package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class EmailReadView extends emailReadDesign implements View {

	public EmailReadView(String subject, String msg, final Window messageWindow) {
		labelSubject.setValue(subject);
		
		textAreaMessage.setValue(msg);
		
		cancelButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				messageWindow.close();
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {	
	}

}
