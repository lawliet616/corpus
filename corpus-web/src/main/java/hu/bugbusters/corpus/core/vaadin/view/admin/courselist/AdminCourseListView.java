package hu.bugbusters.corpus.core.vaadin.view.admin.courselist;

import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerRangeValidator;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.factories.CourseFactory;
import hu.bugbusters.corpus.core.global.Global;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.coursedetails.CourseDetailsView;

public class AdminCourseListView extends AdminCourseListDesign implements View {
	private static final long serialVersionUID = -2216242736099627777L;
	public final static String NAME = "AdminCourseList";
	private BeanContainer<Long, Course> courseDataSource;
	private BeanFieldGroup<Course> courseBinder;
	protected Window editCourseWindow;
	private Dao dao;
	private TextField idField;
	private TextField nameField;
	private TextField creditField;
	private TextField roomField;
	private TextField sizeField;
	private TextArea descriptionArea;
	private ComboBox teacherField;

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
				HorizontalLayout buttonLayout = new HorizontalLayout();
				Button btnEdit = new Button("Szerkesztés");
				Button btnDetails = new Button("Részletek");
				buttonLayout.addComponent(btnEdit);
				buttonLayout.addComponent(btnDetails);
				buttonLayout.setSpacing(true);
				
				btnEdit.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 3795164119475852944L;

					@Override
					public void buttonClick(ClickEvent event) {
						BeanItem<Course> selectedBean = (BeanItem<Course>) source.getItem(itemId);
						openEditWindow(selectedBean.getBean());
					}
				});

				btnDetails.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 1672070317615358296L;

					@Override
					public void buttonClick(ClickEvent event) {
						BeanItem<Course> selectedBean = (BeanItem<Course>) source.getItem(itemId);
						showDetails(selectedBean.getBean());
					}
				});

				return buttonLayout;
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

	protected void showDetails(Course course) {
		Window detailsWindow = new Window("Részletek");
		detailsWindow.setHeight("80%");
		detailsWindow.setWidth("80%");
		detailsWindow.center();
		detailsWindow.setContent(new CourseDetailsView(course, detailsWindow));
		this.getUI().addWindow(detailsWindow);
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
		courseBinder = new BeanFieldGroup<Course>(Course.class);

		courseBinder.setItemDataSource(course);
		verticalLayout.addComponent(createCourseEditForm(courseBinder));

		HorizontalLayout buttonLayout = new HorizontalLayout();
		verticalLayout.addComponent(buttonLayout);
		verticalLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
		Button btnSave = new Button("Mentés");
		Button btnDelete = new Button("Törlés");

		addListenerToButtons(btnSave, btnDelete);

		buttonLayout.addComponent(btnSave);
		buttonLayout.addComponent(btnDelete);
		buttonLayout.setMargin(new MarginInfo(false, true, false, true));
		buttonLayout.setSpacing(true);

		return verticalLayout;
	}

	private void addListenerToButtons(Button btnSave, Button btnDelete) {
		Course bean = courseBinder.getItemDataSource().getBean();
		btnSave.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				saveCourse();
			}
		});
	}

	protected void saveCourse() {
		try {
			Course course = courseBinder.getItemDataSource().getBean();
			RegisteredUser oldTeacher = dao.getUserByFullName(course.getTeacher());
			CourseFactory.quitCourse(oldTeacher, course);
			String newTeacherName = teacherField.getValue().toString();
			course.setTeacher(newTeacherName);
			courseBinder.commit();

			dao.updateEntity(course);
			RegisteredUser newTeacher = dao.getUserByFullName(newTeacherName);
			CourseFactory.registerForCourse(newTeacher, course);
			editCourseWindow.close();
			courseTable.refreshRowCache();
			Notification.show("Sikeresen elmentette a változásokat!", Notification.Type.HUMANIZED_MESSAGE);
		} catch (CommitException e) {
			Notification.show("Nem megfelő formátumú értéket adott meg.", Notification.Type.WARNING_MESSAGE);
		} catch (UserNotFoundException e) {
			Notification.show("Hiba a kiválasztott tanárral!", Notification.Type.ERROR_MESSAGE);
		}
	}

	private Component createCourseEditForm(BeanFieldGroup<Course> courseBinder) {
		FormLayout formLayout = new FormLayout();

		idField = courseBinder.buildAndBind("Előadás azonosítója:", "id", TextField.class);
		idField.setReadOnly(true);
		idField.setWidth("100%");
		formLayout.addComponent(idField);

		nameField = courseBinder.buildAndBind("Előadás címe:", "name", TextField.class);
		nameField.setNullRepresentation("");
		nameField.setWidth("100%");
		nameField.addValidator(createNotNullValidator("Az előadás címe nem lehet üres."));
		formLayout.addComponent(nameField);

		creditField = courseBinder.buildAndBind("Kredit:", "credit", TextField.class);
		creditField.setNullRepresentation("");
		creditField.setWidth("100%");
		creditField.addValidator(new IntegerRangeValidator(
				"A kreditnek " + Global.MIN_CREDIT + " és " + Global.MAX_CREDIT + " Között kell lennie.",
				Global.MIN_CREDIT, Global.MAX_CREDIT));
		creditField.addValidator(createNotNullValidator("Kötelező értéket adni a kredithez."));
		formLayout.addComponent(creditField);

		roomField = courseBinder.buildAndBind("Terem:", "room", TextField.class);
		roomField.setNullRepresentation("");
		roomField.setWidth("100%");
		roomField.addValidator(createNotNullValidator("A terem mező nem lehet üres."));
		formLayout.addComponent(roomField);

		sizeField = courseBinder.buildAndBind("Maximum férőhelyek:", "maxSize", TextField.class);
		sizeField.setNullRepresentation("");
		sizeField.setWidth("100%");
		sizeField.addValidator(createNotNullValidator("A mérethez kell írni érétket."));
		sizeField.addValidator(createNotNegativValidator("A maximum méret nem lehet negatív"));
		formLayout.addComponent(sizeField);

		descriptionArea = courseBinder.buildAndBind("Leírás:", "description", TextArea.class);
		descriptionArea.setNullRepresentation("");
		descriptionArea.setWidth("100%");
		descriptionArea.setHeight("150px");
		formLayout.addComponent(descriptionArea);

		teacherField = new ComboBox("Előadó:");
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

	private Validator createNotNegativValidator(final String message) {
		return new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null && Integer.parseInt(value.toString()) < 0) {
					throw new InvalidValueException(message);
				}
			}
		};
	}

	private Validator createNotNullValidator(final String message) {
		return new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value == null || "".compareTo(value.toString()) == 0) {
					throw new InvalidValueException(message);
				}
			}
		};
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
