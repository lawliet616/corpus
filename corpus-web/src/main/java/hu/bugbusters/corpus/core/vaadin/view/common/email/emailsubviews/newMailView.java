package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class newMailView extends newMailDesign implements View {
	
	public newMailView(final Window newMail) {
		
		cancelButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				newMail.close();
				
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {	
	}

}
