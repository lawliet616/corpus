package hu.bugbusters.corpus.core.vaadin.view.admin.courselist;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.ConfirmationCallback;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.CourseNotFoundException;
import hu.bugbusters.corpus.core.login.Role;

public class AdminCourseListView extends AdminCourseListDesign implements View {
	private static final long serialVersionUID = -2216242736099627777L;
	public final static String NAME = "AdminCourseList";
	BeanContainer<Long, Course> courseDataSource;
	protected Window editCourseWindow;
	private Dao dao;

	public AdminCourseListView() {
		dao = DaoImpl.getInstance();
		courseDataSource = new BeanContainer<Long, Course>(Course.class);
		fillCourseTable();
	}

	private void fillCourseTable() {
		courseDataSource.setBeanIdProperty("id");
		courseDataSource.addAll(dao.listAllCourses());
		courseTable.setContainerDataSource(courseDataSource);

		courseTable.addGeneratedColumn("edit", new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				Button btnEdit = new Button("Szerkesztés");
				btnEdit.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						BeanItem<Course> selectedBean = (BeanItem<Course>) source.getItem(itemId);
						openEditWindow(selectedBean.getBean());
					}
				});

				return btnEdit;
			}
		});

		courseTable.setVisibleColumns("name", "credit", "room", "maxSize", "teacher", "edit");
		courseTable.setColumnHeader("name", "Előadás címe");
		courseTable.setColumnHeader("credit", "Kredit");
		courseTable.setColumnHeader("room", "Terem");
		courseTable.setColumnHeader("maxSize", "Férőhelyek");
		courseTable.setColumnHeader("teacher", "Előadó");
		courseTable.setColumnHeader("edit", "szerkesztés");
	}

	protected void openEditWindow(Course course) {
		editCourseWindow = new Window();
		editCourseWindow.setHeight("600px");
		editCourseWindow.setWidth("700px");
		editCourseWindow.center();
		editCourseWindow.setContent(createCourseEditLayout(course));
		this.getUI().addWindow(editCourseWindow);
	}

	private Component createCourseEditLayout(Course course) {
		VerticalLayout verticalLayout = new VerticalLayout();
		final BeanFieldGroup<Course> courseBinder = new BeanFieldGroup<Course>(Course.class);

		courseBinder.setItemDataSource(course);
		verticalLayout.addComponent(createCourseEditForm(courseBinder));

		HorizontalLayout buttonLayout = new HorizontalLayout();
		verticalLayout.addComponent(buttonLayout);
		verticalLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
		Button btnSave = new Button("Mentés");
		Button btnDelete = new Button("Törlés");

		addListenerToButtons(courseBinder, btnSave, btnDelete);

		buttonLayout.addComponent(btnSave);
		buttonLayout.addComponent(btnDelete);
		buttonLayout.setMargin(new MarginInfo(false, true, false, true));
		buttonLayout.setSpacing(true);

		return verticalLayout;
	}

	private void addListenerToButtons(BeanFieldGroup<Course> courseBinder, Button btnSave, Button btnDelete) {
		try {
			courseBinder.commit();
			Course bean = courseBinder.getItemDataSource().getBean();
			if (bean.getName() == null || bean.getRoom() == null || bean.getTeacher() == null) {
				throw new Exception("A mező értékek nem felelnek meg.");
			}
		} catch (CommitException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
	}

	private Component createCourseEditForm(BeanFieldGroup<Course> courseBinder) {
		FormLayout formLayout = new FormLayout();

		TextField idField = courseBinder.buildAndBind("Előadás azonosítója:", "id", TextField.class);
		idField.setReadOnly(true);
		idField.setWidth("100%");
		formLayout.addComponent(idField);

		TextField nameField = courseBinder.buildAndBind("Előadás címe:", "name", TextField.class);
		nameField.setNullRepresentation("");
		nameField.setWidth("100%");
		formLayout.addComponent(nameField);

		TextField creditField = courseBinder.buildAndBind("Kredit:", "credit", TextField.class);
		creditField.setNullRepresentation("");
		creditField.setWidth("100%");
		formLayout.addComponent(creditField);

		TextField roomField = courseBinder.buildAndBind("Terem:", "room", TextField.class);
		roomField.setNullRepresentation("");
		roomField.setWidth("100%");
		formLayout.addComponent(roomField);

		TextField sizeField = courseBinder.buildAndBind("Maximum férőhelyek:","maxSize",TextField.class);
		sizeField.setNullRepresentation("");
		sizeField.setWidth("100%");
		formLayout.addComponent(sizeField);
		
		TextArea descriptionArea = courseBinder.buildAndBind("Leírás:","description", TextArea.class);
		descriptionArea.setNullRepresentation("");
		descriptionArea.setWidth("100%");
		descriptionArea.setHeight("150px");
		formLayout.addComponent(descriptionArea);
		
		ComboBox teacherField = new ComboBox("Előadó:");
		teacherField.setNullSelectionAllowed(false);
		Course bean = courseBinder.getItemDataSource().getBean();
		for (RegisteredUser teacher : dao.listAllUsers()) {
			if (teacher.getRole() == Role.TEACHER) {
				teacherField.addItem(teacher.getFullName());
			}
		}
		teacherField.setValue(bean.getTeacher());
		teacherField.setWidth("100%");
		formLayout.addComponent(teacherField);

		formLayout.setMargin(true);
		formLayout.setSpacing(true);

		return formLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
