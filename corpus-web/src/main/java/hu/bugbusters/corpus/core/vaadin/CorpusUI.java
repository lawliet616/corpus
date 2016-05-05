package hu.bugbusters.corpus.core.vaadin;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class CorpusUI extends UI{
	
	@Override
	protected void init(VaadinRequest request) {
		this.setContent(new Label("Hello"));
	}

}
