package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.registeredUser")
	private Set<TakenCourse> courses = new HashSet<>();

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sentBy")
	private Set<Message> sentMails = new HashSet<>();

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.registeredUser")
	private Set<Inbox> receivedMails = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RegisteredUser that = (RegisteredUser) o;

		if (!id.equals(that.id)) return false;
		if (!username.equals(that.username)) return false;
		return email.equals(that.email);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + username.hashCode();
		result = 31 * result + email.hashCode();
		return result;
	}
}
