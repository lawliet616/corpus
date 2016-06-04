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
	@Column(name = "id", nullable = false)
	private Integer id;

	@Getter
	@Setter
	@Column(name = "min_length", nullable = false)
	private int minLength;

	@Getter
	@Setter
	@Column(name = "max_length", nullable = false)
	private int maxLength;

	@Getter
	@Setter
	@Column(name = "min_dig_char", nullable = false)
	private int minDigChar;

	@Getter
	@Setter
	@Column(name = "min_upper_char", nullable = false)
	private int minUpperChar;

	@Getter
	@Setter
	@Column(name = "min_lower_char", nullable = false)
	private int minLowerChar;

	@Getter
	@Setter
	@Column(name = "min_rules", nullable = false)
	private int minRules;
}
