package hu.bugbusters.corpus.core.vaadin.view.admin.newcourse;

import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.CourseNotFoundException;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.factories.CourseFactory;
import hu.bugbusters.corpus.core.login.Role;

@SuppressWarnings("serial")
public class NewCourseView extends NewCourseDesign implements View {
	public static final String NAME = "NewCourse";
	private Dao dao;

	public NewCourseView() {
		dao = DaoImpl.getInstance();
		fillCombobox();
		cmbTeachers.setNullSelectionAllowed(false);
		btnSave.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -1904494345888185504L;

			@Override
			public void buttonClick(ClickEvent event) {
				showConfirmDialog();
			}
		});

		cmbTeachers.setNullSelectionAllowed(false);
	}

	protected void showConfirmDialog() {
		ConfirmDialog.show(getUI(), "Biztos?", "Biztos vagy benne?", "Igen, biztos!", "Nem igazán.",
				new ConfirmDialog.Listener() {
					private static final long serialVersionUID = -1318588884359394783L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							saveCourse();
						}
					}
				});
	}

	private void fillCombobox() {
		List<RegisteredUser> userList = dao.listAllUsers();
		for (RegisteredUser user : userList) {
			if (user.getRole() == Role.TEACHER) {
				cmbTeachers.addItem(user.getFullName());
			}
		}
	}

	protected void saveCourse() {
		if (checkValues() && isCoursenameUnique()) {
			try {
				Course course = CourseFactory.createAndSaveCourse(txtName.getValue(), txtRoom.getValue(),
						Integer.parseInt(txtCredit.getValue()), cmbTeachers.getValue().toString(),
						Integer.parseInt(txtMaxUser.getValue()), txtDescription.getValue());

				RegisteredUser teacher = dao.getUserByFullName(cmbTeachers.getValue().toString());
				CourseFactory.registerForCourse(teacher, course);
				Notification.show("Sikeresen elmentette az adatokat.", Notification.Type.HUMANIZED_MESSAGE);
				txtCredit.clear();
				txtName.clear();
				txtRoom.clear();
				txtMaxUser.clear();
				txtDescription.clear();
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isCoursenameUnique() {
		try {
			dao.getCourseByName(txtName.getValue());
			Notification.show("Ezen a néven már szerepel előadás.", Notification.Type.WARNING_MESSAGE);
			return false;
		} catch (CourseNotFoundException e) {
			return true;
		}
	}

	private boolean checkValues() {
		try {
			if (Integer.parseInt(txtCredit.getValue()) < 0 || Integer.parseInt(txtMaxUser.getValue()) < 0) {
				Notification.show("A kredit és a maximum létszám csak pozitív lehet.",
						Notification.Type.WARNING_MESSAGE);
				return false;
			}
			if (txtName.getValue() != "" && txtRoom.getValue() != "" && cmbTeachers.getValue() != null) {
				return true;
			}
			Notification.show("Nem adott meg minden szükséges információt.", Notification.Type.WARNING_MESSAGE);
			return false;
		} catch (NumberFormatException e) {
			Notification.show("A kreditnek és a maximum létszámnak csak egész számot lehet megadni!",
					Notification.Type.WARNING_MESSAGE);
			return false;
		}

	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
