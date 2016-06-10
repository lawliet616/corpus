package hu.bugbusters.corpus.core.vaadin.view.admin.homepage;

import org.vaadin.sliderpanel.SliderPanel;
import org.vaadin.sliderpanel.SliderPanelBuilder;
import org.vaadin.sliderpanel.SliderPanelStyles;
import org.vaadin.sliderpanel.client.SliderMode;
import org.vaadin.sliderpanel.client.SliderTabPosition;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class AdminHomepageView extends AdminHomepageDesign implements View {
	private static final long serialVersionUID = 6600854813360310972L;
	public static final String Name="Homepage";
	private RegisteredUser user;
	private String txt;
	
	public AdminHomepageView() {
		user = Login.getLoggedInUser();
		lblWelcome.setValue("Üdvözöllek "+user.getFullName()+"! Jó munkát!");
		tesxtSetting();
		slider();
	}

	private void tesxtSetting() {
		txt = "1. Ha túl erősen szorítod, eltöröd.\n Ha fogod, idővel elhasad.\nTartsd a tenyereden,\nés örökre a tiéd marad.";
	}

	private void slider() {
		
		TextArea text = new TextArea();
		text.setValue(txt);
		text.setSizeFull();
		
		SliderPanel sliderPanel = new SliderPanelBuilder(new Label("Itt lehetnek hírek vagy egyéb ötlet ami csak van jöhet!"))
				  .caption("Hírek")
				  .mode(SliderMode.RIGHT)
				  .fixedContentSize(200)
				  .flowInContent(true)
				  .autoCollapseSlider(true)
				  .tabPosition(SliderTabPosition.MIDDLE)
				  .style(SliderPanelStyles.COLOR_WHITE)
				  .build();
		
		lay.addComponent(sliderPanel);
		
		
		lay.setExpandRatio(layout, 1);
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}
