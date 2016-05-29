package hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.vaadin.CorpusUI;

public class ChangeSelfDetailsView extends ChangeSelfDetailsDesign implements View {
	public static final String NAME = "ChangeSelfDetails";


	public ChangeSelfDetailsView() {
		btnCancel.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(SelfDetailsView.NAME);
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
