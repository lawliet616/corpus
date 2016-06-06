package hu.bugbusters.corpus.core.vaadin.view.admin.settings;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;

import hu.bugbusters.corpus.core.bean.PasswordSettings;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.global.Global;
import hu.bugbusters.corpus.core.password.PasswordRows;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SettingsView extends SettingsDesign implements View {
	public static final String NAME = "Settings";
	private Dao dao = DaoImpl.getInstance();
	private PasswordSettings settings;
	private int maxLength;
	private int minDigChar;
	private int minLength;
	private int minLowerChar;
	private int minRules;
	private int minUpperChar;

	public SettingsView() {
		setCurrentValues();

		btnSave.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				CheckValues();
			}
		});

		btnReset.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				resetToDefaultValues();
			}
		});

	}

	protected void resetToDefaultValues() {
		settings.setId(PasswordRows.CUSTOM.toInteger());
		dao.deleteEntity(settings);
		setCurrentValues();
		Notification.show("Sikeresen visszaállította az alapértékeket.", Notification.Type.HUMANIZED_MESSAGE);
	}

	private void setCurrentValues() {
		settings = dao.getPasswordSettings(PasswordRows.CUSTOM);
		if (settings == null) {
			settings = dao.getPasswordSettings(PasswordRows.DEFAULT);
		}
		txtMaxLength.setValue(String.valueOf(settings.getMaxLength()));
		txtMinDigChar.setValue(String.valueOf(settings.getMinDigChar()));
		txtMinLength.setValue(String.valueOf(settings.getMinLength()));
		txtMinLowerChar.setValue(String.valueOf(settings.getMinLowerChar()));
		txtMinRules.setValue(String.valueOf(settings.getMinRules()));
		txtMinUpperChar.setValue(String.valueOf(settings.getMinUpperChar()));
	}

	protected void CheckValues() {
		if (valuesIsNumber()) {
			if (isAnyValueNegative(maxLength, minDigChar, minLength, minLowerChar, minRules, minUpperChar)) {
				if (minLength >= Global.MIN_PASSWORD_LENGTH) {
					if (maxLength <= Global.MAX_PASSWORD_LENGTH) {
						if (minRules <= Global.NUMBER_OF_PASSWORD_RULES) {
							if (maxLength >= minLength) {
								if (minDigChar + minLowerChar + minUpperChar <= minLength) {
									insertValuesIntoDatabase();
								} else {
									Notification.show(
											"A megadott karakterisztikus szabályok összege nem lehet hosszabb mint a megadott legrövidebb jelszóhossz.",
											Notification.Type.WARNING_MESSAGE);
								}
							} else {
								Notification.show("A maximális jelszóhossz nem lehet rövidebb mint a minimális.",
										Notification.Type.WARNING_MESSAGE);
							}
						} else {
							Notification.show(
									"Összesen csak " + Global.NUMBER_OF_PASSWORD_RULES
											+ " karakterisztikus szabály van. Ennél többet nem lehet megadni.",
									Notification.Type.WARNING_MESSAGE);
						}
					} else {
						Notification.show("A jelszó maximális hossza nem lehet több mint " + Global.MAX_PASSWORD_LENGTH,
								Notification.Type.WARNING_MESSAGE);
					}
				} else {
					Notification.show("A jelszó minimális hossza nem lehet rövidebb mint " + Global.MAX_PASSWORD_LENGTH,
							Notification.Type.WARNING_MESSAGE);
				}
			} else {
				Notification.show("A megadott érték nem lehet 0-nál kissebb.", Notification.Type.WARNING_MESSAGE);
			}
		}
	}

	private void insertValuesIntoDatabase() {
		settings.setMaxLength(maxLength);
		settings.setMinDigChar(minDigChar);
		settings.setMinLength(minLength);
		settings.setMinLowerChar(minLowerChar);
		settings.setMinRules(minRules);
		settings.setMinUpperChar(minUpperChar);
		settings.setId(PasswordRows.CUSTOM.toInteger());

		if (dao.getPasswordSettings(PasswordRows.CUSTOM) == null) {
			dao.saveEntity(settings);
		} else {
			dao.updateEntity(settings);
		}
		Notification.show("A mentés sikeres volt.", Notification.Type.HUMANIZED_MESSAGE);
	}

	private boolean isAnyValueNegative(int maxLength, int minDigChar, int minLegth, int minLowerChar, int minRules,
			int minUpperchar) {
		return maxLength >= 0 && minDigChar >= 0 && minLegth >= 0 && minLowerChar >= 0 && minRules >= 0
				&& minUpperchar >= 0;
	}

	private boolean valuesIsNumber() {
		try {
			maxLength = Integer.parseInt(txtMaxLength.getValue());
			minDigChar = Integer.parseInt(txtMinDigChar.getValue());
			minLength = Integer.parseInt(txtMinLength.getValue());
			minLowerChar = Integer.parseInt(txtMinLowerChar.getValue());
			minRules = Integer.parseInt(txtMinRules.getValue());
			minUpperChar = Integer.parseInt(txtMinUpperChar.getValue());
			return true;
		} catch (NumberFormatException e) {
			Notification.show("Csak számokat adhat meg.", Notification.Type.WARNING_MESSAGE);
			return false;
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
