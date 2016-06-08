package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.util.RoleConverter;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
public class RegisteredUser implements Serializable {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "registered_user_seq", allocationSize = 1)
	private Long id;

	@Getter
	@Setter
	private String username;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	@Convert(converter = RoleConverter.class)
	private Role role;

	@Getter
	@Setter
	private String fullName;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "registered_user_course", catalog = "CORPUS", joinColumns = {
			@JoinColumn(name = "r_id", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "c_id",
					nullable = false, updatable = false)})
	private Set<Course> courses = new HashSet<>();

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sentBy")
	private Set<Message> sentMails = new HashSet<>();

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.registeredUser")
	private Set<Inbox> receivedMails = new HashSet<>();
}
