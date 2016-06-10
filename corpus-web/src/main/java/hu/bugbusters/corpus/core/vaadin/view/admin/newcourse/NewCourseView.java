package hu.bugbusters.corpus.core.vaadin.view.admin.newcourse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.factories.CourseFactory;
import hu.bugbusters.corpus.core.login.Role;

@SuppressWarnings("serial")
public class NewCourseView extends NewCourseDesign implements View {
	private static final long serialVersionUID = -5564777106849244090L;
	public static final String NAME = "NewCourse";
	private Dao dao;

	public NewCourseView() {
		dao = DaoImpl.getInstance();
		fillCombobox();
		btnSave.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -1904494345888185504L;

			@Override
			public void buttonClick(ClickEvent event) {
				saveCourse();
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
		if (checkValues()) {
			try {
				CourseFactory factory = CourseFactory.getCourseFactory();
				Course course = factory.createAndSaveCourse(txtName.getValue(), txtRoom.getValue(),
						Integer.parseInt(txtCredit.getValue()), cmbTeachers.getValue().toString());

				RegisteredUser teacher = dao.getUserByFullName(cmbTeachers.getValue().toString());
				Set<Course> courseSet = new HashSet<>();
				courseSet.add(course);
				teacher.setCourses(courseSet);
				dao.updateEntity(teacher);
				Notification.show("Sikeresen elmentette az adatokat.", Notification.Type.HUMANIZED_MESSAGE);
				txtCredit.clear();
				txtName.clear();
				txtRoom.clear();
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkValues() {
		try {
			Integer.parseInt(txtCredit.getValue());
			if (txtName.getValue() != "" && txtRoom.getValue() != "" && cmbTeachers.getValue() != null) {
				return true;
			}
			Notification.show("Nem adott meg minden szükséges információt.", Notification.Type.WARNING_MESSAGE);
			return false;
		} catch (NumberFormatException e) {
			Notification.show("Kreditnek csak egész számot lehet megadni!", Notification.Type.WARNING_MESSAGE);
			txtCredit.clear();
			return false;
		}

	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
