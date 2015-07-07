package net.relaxism.utils.jdbc.parameter;

import net.relaxism.utils.jdbc.SQLType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class Parameter {

	protected final SQLType type;
	protected final Integer scaleOrLength;

	public Parameter(SQLType type) {
		this(type, null);
	}

	public Parameter(SQLType type, Integer scaleOrLength) {
		this.type = type;
		this.scaleOrLength = scaleOrLength;
	}

	public SQLType getType() {
		return type;
	}

	public Integer getScaleOrLength() {
		return scaleOrLength;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
