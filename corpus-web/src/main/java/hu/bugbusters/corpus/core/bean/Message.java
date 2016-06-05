package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import hu.bugbusters.corpus.core.bean.join.Inbox;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "message", catalog = "CORPUS")
public class Message implements Serializable {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "message_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
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
	private Set<RegisteredUser> sentMessages = new HashSet<>();

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.message")
	private Set<Inbox> receivedMessages = new HashSet<>();
}
