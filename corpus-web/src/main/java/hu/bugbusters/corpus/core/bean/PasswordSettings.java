package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
public class PasswordSettings implements Serializable {
	@Getter
	@Setter
	@Id
	private Integer id;

	@Getter
	@Setter
	private int minLength;

	@Getter
	@Setter
	private int maxLength;

	@Getter
	@Setter
	private int minDigChar;

	@Getter
	@Setter
	private int minUpperChar;

	@Getter
	@Setter
	private int minLowerChar;

	@Getter
	@Setter
	private int minRules;
}
