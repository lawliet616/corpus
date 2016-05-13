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
import hu.bugbusters.corpus.core.util.RoleConverter;

@Entity
@Table(name = "registereduser")
public class RegisteredUser implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "registereduser_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "role", nullable = false)
	@Convert(converter = RoleConverter.class)
	private Role role;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "registereduser_course", joinColumns = {
			@JoinColumn(name = "r_id", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "c_id",
					nullable = false, updatable = false)})
	private Set<Course> courses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
}
