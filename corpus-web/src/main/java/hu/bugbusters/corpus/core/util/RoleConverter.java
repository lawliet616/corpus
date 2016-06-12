package hu.bugbusters.corpus.core.util;

import javax.persistence.AttributeConverter;

import hu.bugbusters.corpus.core.login.Role;

public class RoleConverter implements AttributeConverter<Role, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Role role) {
		switch(role) {
			case ADMIN:
				return 2;
			case TEACHER:
				return 1;
			case USER:
				return 0;
			default:
				throw new IllegalArgumentException("Unknown role: " + role);		
		}
	}

	@Override
	public Role convertToEntityAttribute(Integer data) {
		switch(data) {
			case 2:
				return Role.ADMIN;
			case 1:
				return Role.TEACHER;
			case 0:
				return Role.USER;
			default:
				throw new IllegalArgumentException("Unknown role from database:" + data);
		}
	}

}
