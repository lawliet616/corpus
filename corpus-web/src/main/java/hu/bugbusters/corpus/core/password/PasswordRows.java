package hu.bugbusters.corpus.core.password;

//@formatter:off
public enum PasswordRows {
	DEFAULT(1),
	CUSTOM(2);
	
	private Integer id;
	
	private PasswordRows(Integer id) {
		this.id = id;
	}
	
	public Integer toInteger() {
		return id;
	}
}
