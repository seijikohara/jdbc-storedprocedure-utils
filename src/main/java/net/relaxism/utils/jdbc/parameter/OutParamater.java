package net.relaxism.utils.jdbc.parameter;

import net.relaxism.utils.jdbc.SQLType;

public class OutParamater extends Parameter {

	private final String name;

	public OutParamater(String name, SQLType type) {
		this(name, type, null);
	}

	public OutParamater(String name, SQLType type, Integer scaleOrLength) {
		super(type, scaleOrLength);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
