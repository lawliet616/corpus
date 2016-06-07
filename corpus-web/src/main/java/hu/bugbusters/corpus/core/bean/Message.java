package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings({"serial", "MismatchedQueryAndUpdateOfCollection"})
@Entity
@Table(name = "message", catalog = "CORPUS")
public class Message implements Serializable {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "message_seq", allocationSize = 1)
	private Long id;

	@Getter
	@Setter
	private Long creatorId;

	@Getter
	@Setter
	private String subject;
	
	@Getter
	@Setter
	private String message;

	@Getter
	@Setter
	private Timestamp time;

	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "sentMails")
	private Set<RegisteredUser> sentMessages = new HashSet<>();

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.message")
	private Set<Inbox> receivedMessages = new HashSet<>();

	public Inbox getInboxByMesssageId(Long id){
		for (Inbox receivedMessage : receivedMessages){
			if(Objects.equals(receivedMessage.getMessage().getId(), id)){
				return receivedMessage;
			}
		}
		return null;
	}
}
