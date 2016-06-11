package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
public class Message implements Serializable {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "message_seq", allocationSize = 1)
	private Long id;

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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private RegisteredUser sentBy;

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.message")
	private Set<Inbox> receivedBy = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Message message1 = (Message) o;

		if (!id.equals(message1.id)) return false;
		if (!subject.equals(message1.subject)) return false;
		if (!message.equals(message1.message)) return false;
		return time.equals(message1.time);

	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + subject.hashCode();
		result = 31 * result + message.hashCode();
		result = 31 * result + time.hashCode();
		return result;
	}
}
