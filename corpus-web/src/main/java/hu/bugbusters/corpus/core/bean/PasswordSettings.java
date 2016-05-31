package hu.bugbusters.corpus.core.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "password_settings")
public class PasswordSettings implements Serializable {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "min_length", nullable = false)
	private int minLength;
	
	@Column(name = "max_length", nullable = false)
	private int maxLength;
	
	@Column(name = "min_dig_char", nullable = false)
	private int minDigChar;
	
	@Column(name = "min_upper_char", nullable = false)
	private int minUpperChar;
	
	@Column(name = "min_lower_char", nullable = false)
	private int minLowerChar;
	
	@Column(name = "min_rules", nullable = false)
	private int minRules;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMinDigChar() {
		return minDigChar;
	}

	public void setMinDigChar(int minDigChar) {
		this.minDigChar = minDigChar;
	}

	public int getMinUpperChar() {
		return minUpperChar;
	}

	public void setMinUpperChar(int minUpperChar) {
		this.minUpperChar = minUpperChar;
	}

	public int getMinLowerChar() {
		return minLowerChar;
	}

	public void setMinLowerChar(int minLowerChar) {
		this.minLowerChar = minLowerChar;
	}

	public int getMinRules() {
		return minRules;
	}

	public void setMinRules(int minRules) {
		this.minRules = minRules;
	}
}
