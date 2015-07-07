package net.relaxism.utils.jdbc.parameter;

import net.relaxism.utils.jdbc.SQLType;

public class InParamater extends Parameter {

	private final String value;

	public InParamater(String value, SQLType type) {
		this(value, type, null);
	}

	public InParamater(String value, SQLType type, Integer scaleOrLength) {
		super(type, scaleOrLength);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
