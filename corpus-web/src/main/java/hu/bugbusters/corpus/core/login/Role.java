package hu.bugbusters.corpus.core.login;

import hu.bugbusters.corpus.core.global.Global;

//@formatter:off
public enum Role {
	ADMIN(Global.ROLE_ADMIN_NAME),
	TEACHER(Global.ROLE_TEACHER_NAME),
	USER(Global.ROLE_USER_NAME);

	private String roleAsText;

	private Role(String text) {
		this.roleAsText = text;
	}

	@Override
	public String toString() {
		return roleAsText;
	}
}
