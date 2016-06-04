package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.password.Password;
import hu.bugbusters.corpus.core.password.PasswordStorage;
import hu.bugbusters.corpus.core.util.RoleConverter;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "registereduser")
public class RegisteredUser implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "registereduser_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Getter
	@Setter
	@Column(name = "password", nullable = false)
	private String password;

	@Getter
	@Setter
	@Column(name = "role", nullable = false)
	@Convert(converter = RoleConverter.class)
	private Role role;

	@Getter
	@Setter
	@Column(name = "fullname")
	private String fullname;

	@Getter
	@Setter
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "registereduser_course", joinColumns = {
			@JoinColumn(name = "r_id", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "c_id",
					nullable = false, updatable = false)})
	private Set<Course> courses;
}
