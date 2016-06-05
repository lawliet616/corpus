package hu.bugbusters.corpus.core.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "course", catalog = "CORPUS", uniqueConstraints = {
		@UniqueConstraint(columnNames = "name")})
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
	private String room;

	@Getter
	@Setter
	@Column(name = "credit")
	private int credit;

	@Getter
	@Setter
	@Column(name = "teacher")
	private String teacher;

	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
	private Set<RegisteredUser> students = new HashSet<>();
}
