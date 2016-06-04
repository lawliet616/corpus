package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "registereduser")
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
	@Column(name = "subject")
	private String subject;
	
	@Getter
	@Setter
	@Column(name = "message")
	private String message;
}
