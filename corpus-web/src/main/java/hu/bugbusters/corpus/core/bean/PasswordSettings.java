package hu.bugbusters.corpus.core.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "password_settings")
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
