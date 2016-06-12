package hu.bugbusters.corpus.core.vaadin.view.admin.courselist;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

public class AdminCourseListView extends AdminCourseListDesign implements View {
	private static final long serialVersionUID = -2216242736099627777L;
	public final static String NAME = "AdminCourseList";
	private Dao dao;

	public AdminCourseListView() {
		dao = DaoImpl.getInstance();
		fillCourseTable();
	}

	private void fillCourseTable() {
		BeanContainer<Long, Course> courseDataSource = new BeanContainer<Long, Course>(Course.class);
		courseDataSource.setBeanIdProperty("id");
		courseDataSource.addAll(dao.listAllCourses());
		courseTable.setContainerDataSource(courseDataSource);

		courseTable.setVisibleColumns("name", "credit", "room", "teacher");
		courseTable.setColumnHeader("name", "Előadás címe");
		courseTable.setColumnHeader("credit", "kredit");
		courseTable.setColumnHeader("room", "terem");
		courseTable.setColumnHeader("teacher", "előadó");
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
