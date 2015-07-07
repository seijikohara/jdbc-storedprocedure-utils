package net.relaxism.utils.jdbc.procedure;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.relaxism.utils.jdbc.parameter.InParamater;
import net.relaxism.utils.jdbc.parameter.OutParamater;
import net.relaxism.utils.jdbc.parameter.Parameter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class StoredProcedure {

	protected final String name;

	public StoredProcedure(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	protected void registerParamters(CallableStatement statement,
			int startIndex, Object... parameters) throws SQLException {
		int index = startIndex;
		for (Object parameter : parameters) {
			if (parameter instanceof OutParamater) {
				Parameter outParamater = (Parameter) parameter;
				if (outParamater.getScaleOrLength() == null) {
					statement.registerOutParameter(index, outParamater
							.getType().getTypeCode());
				} else {
					statement.registerOutParameter(index, outParamater
							.getType().getTypeCode(), outParamater
							.getScaleOrLength());
				}
			} else if (parameter instanceof InParamater) {
				InParamater inParamater = (InParamater) parameter;
				if (inParamater.getScaleOrLength() == null) {
					statement.setObject(index, inParamater.getValue(),
							inParamater.getType().getTypeCode());
				} else {
					statement.setObject(index, inParamater.getValue(),
							inParamater.getType().getTypeCode(),
							inParamater.getScaleOrLength());
				}

			} else {
				statement.setObject(index, parameter);
			}
			index++;
		}
	}

	protected String generateParameterPraceHolder(int count) {
		if (count <= 0) {
			return "";
		}

		final char holderChar = '?';
		StringBuilder builder = new StringBuilder();
		builder.append(holderChar);
		for (int i = 2; i <= count; i++) {
			builder.append(',').append(holderChar);
		}

		return builder.toString();
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, true);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	protected static abstract class Return {

		protected final Map<OutParamater, Object> outParamaters;

		public Return(CallableStatement statement, int outParamStartIndex,
				Object... parameters) throws SQLException {
			this.outParamaters = Collections
					.unmodifiableMap(processOutParameters(statement,
							outParamStartIndex, parameters));
		}

		private Map<OutParamater, Object> processOutParameters(
				CallableStatement statement, int outParamStartIndex,
				Object... parameters) throws SQLException {
			Map<OutParamater, Object> outParamaters = new HashMap<OutParamater, Object>();
			int paramIndex = outParamStartIndex;
			for (Object param : parameters) {
				if (param instanceof OutParamater) {
					OutParamater outParamater = (OutParamater) param;
					outParamaters.put(outParamater,
							statement.getObject(paramIndex));
				}
				paramIndex++;
			}
			return outParamaters;
		}

		public Array getArray(OutParamater outParamater) {
			return (Array) outParamaters.get(outParamater);
		}

		public BigDecimal getBigDecimal(OutParamater outParamater) {
			return (BigDecimal) outParamaters.get(outParamater);
		}

		public Blob getBlob(OutParamater outParamater) {
			return (Blob) outParamaters.get(outParamater);
		}

		public Boolean getBoolean(OutParamater outParamater) {
			return (Boolean) outParamaters.get(outParamater);
		}

		public Byte getByte(OutParamater outParamater) {
			return (Byte) outParamaters.get(outParamater);
		}

		public byte[] getBytes(OutParamater outParamater) {
			return (byte[]) outParamaters.get(outParamater);
		}

		public Clob getClob(OutParamater outParamater) {
			return (Clob) outParamaters.get(outParamater);
		}

		public java.sql.Date getDate(OutParamater outParamater) {
			return (java.sql.Date) outParamaters.get(outParamater);
		}

		public Double getDouble(OutParamater outParamater) {
			return (Double) outParamaters.get(outParamater);
		}

		public Float getFloat(OutParamater outParamater) {
			return (Float) outParamaters.get(outParamater);
		}

		public Integer getInteger(OutParamater outParamater) {
			return (Integer) outParamaters.get(outParamater);
		}

		public Long getLong(OutParamater outParamater) {
			return (Long) outParamaters.get(outParamater);
		}

		public Object getObject(OutParamater outParamater) {
			return outParamaters.get(outParamater);
		}

		public Ref getRef(OutParamater outParamater) {
			return (Ref) outParamaters.get(outParamater);
		}

		public RowId getRowId(OutParamater outParamater) {
			return (RowId) outParamaters.get(outParamater);
		}

		public Short getShort(OutParamater outParamater) {
			return (Short) outParamaters.get(outParamater);
		}

		public String getString(OutParamater outParamater) {
			return (String) outParamaters.get(outParamater);
		}

		public java.sql.Time getTime(OutParamater outParamater) {
			return (java.sql.Time) outParamaters.get(outParamater);
		}

		public java.sql.Timestamp getTimestamp(OutParamater outParamater) {
			return (java.sql.Timestamp) outParamaters.get(outParamater);
		}

		public Map<OutParamater, Object> getOutParamaters() {
			return outParamaters;
		}

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this, false);
		}

		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj, true);
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}

	}

}
