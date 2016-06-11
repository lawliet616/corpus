package hu.bugbusters.corpus.core.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
public class Course implements Serializable {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "course_seq", allocationSize = 1)
	private Long id;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String room;

	@Getter
	@Setter
	private int credit;

	@Getter
	@Setter
	private String teacher;

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.course")
	private Set<TakenCourse> students = new HashSet<>();
}
