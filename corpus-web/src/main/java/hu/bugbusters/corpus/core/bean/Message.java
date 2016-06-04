package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "message")
public class Message implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "message_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	@Column(name = "subject", nullable = false)
	private String subject;
	
	@Getter
	@Setter
	@Column(name = "message", nullable = false)
	private String message;

	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "sentMails")
	private Set<RegisteredUser> registeredUserSent;

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.message")
	private Set<RegisteredUser> registeredUserInbox;
}
