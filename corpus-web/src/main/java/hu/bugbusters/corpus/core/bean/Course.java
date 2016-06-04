package hu.bugbusters.corpus.core.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "course")
public class Course implements Serializable {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "course_seq", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Getter
	@Setter
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Getter
	@Setter
	@Column(name = "room")
	private int room;

	@Getter
	@Setter
	@Column(name = "credit")
	private int credit;

	@Getter
	@Setter
	@Column(name = "professor")
	private String professor;

	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
	private Set<RegisteredUser> students;
}
