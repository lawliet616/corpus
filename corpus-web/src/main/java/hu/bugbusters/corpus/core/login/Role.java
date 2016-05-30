package hu.bugbusters.corpus.core.login;

public enum Role {
	ADMIN("Rendszergazda"),
	TEACHER("Tanár"),
	USER("Diák");
	
	private String roleAsText;
	
	private Role(String text) {
		this.roleAsText = text;
	}
	
	@Override
	public String toString() {
		return roleAsText;
	}
}
