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

import net.relaxism.utils.jdbc.parameter.InParameter;
import net.relaxism.utils.jdbc.parameter.OutParameter;
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

	protected void registerParameters(CallableStatement statement,
			int startIndex, Object... parameters) throws SQLException {
		int index = startIndex;
		for (Object parameter : parameters) {
			if (parameter instanceof OutParameter) {
				Parameter outParameter = (Parameter) parameter;
				if (outParameter.getScaleOrLength() == null) {
					statement.registerOutParameter(index, outParameter
							.getType().getTypeCode());
				} else {
					statement.registerOutParameter(index, outParameter
							.getType().getTypeCode(), outParameter
							.getScaleOrLength());
				}
			} else if (parameter instanceof InParameter) {
				InParameter inParameter = (InParameter) parameter;
				if (inParameter.getScaleOrLength() == null) {
					statement.setObject(index, inParameter.getValue(),
							inParameter.getType().getTypeCode());
				} else {
					statement.setObject(index, inParameter.getValue(),
							inParameter.getType().getTypeCode(),
							inParameter.getScaleOrLength());
				}

			} else {
				statement.setObject(index, parameter);
			}
			index++;
		}
	}

	protected String generateParameterPlaceHolder(int count) {
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

		protected final Map<OutParameter, Object> outParameters;

		public Return(CallableStatement statement, int outParamStartIndex,
				Object... parameters) throws SQLException {
			this.outParameters = Collections
					.unmodifiableMap(processOutParameters(statement,
							outParamStartIndex, parameters));
		}

		private Map<OutParameter, Object> processOutParameters(
				CallableStatement statement, int outParamStartIndex,
				Object... parameters) throws SQLException {
			Map<OutParameter, Object> outParameters = new HashMap<OutParameter, Object>();
			int paramIndex = outParamStartIndex;
			for (Object param : parameters) {
				if (param instanceof OutParameter) {
					OutParameter outParameter = (OutParameter) param;
					outParameters.put(outParameter,
							statement.getObject(paramIndex));
				}
				paramIndex++;
			}
			return outParameters;
		}

		public Array getArray(OutParameter outParameter) {
			return (Array) outParameters.get(outParameter);
		}

		public BigDecimal getBigDecimal(OutParameter outParameter) {
			return (BigDecimal) outParameters.get(outParameter);
		}

		public Blob getBlob(OutParameter outParameter) {
			return (Blob) outParameters.get(outParameter);
		}

		public Boolean getBoolean(OutParameter outParameter) {
			return (Boolean) outParameters.get(outParameter);
		}

		public Byte getByte(OutParameter outParameter) {
			return (Byte) outParameters.get(outParameter);
		}

		public byte[] getBytes(OutParameter outParameter) {
			return (byte[]) outParameters.get(outParameter);
		}

		public Clob getClob(OutParameter outParameter) {
			return (Clob) outParameters.get(outParameter);
		}

		public java.sql.Date getDate(OutParameter outParameter) {
			return (java.sql.Date) outParameters.get(outParameter);
		}

		public Double getDouble(OutParameter outParameter) {
			return (Double) outParameters.get(outParameter);
		}

		public Float getFloat(OutParameter outParameter) {
			return (Float) outParameters.get(outParameter);
		}

		public Integer getInteger(OutParameter outParameter) {
			return (Integer) outParameters.get(outParameter);
		}

		public Long getLong(OutParameter outParameter) {
			return (Long) outParameters.get(outParameter);
		}

		public Object getObject(OutParameter outParameter) {
			return outParameters.get(outParameter);
		}

		public Ref getRef(OutParameter outParameter) {
			return (Ref) outParameters.get(outParameter);
		}

		public RowId getRowId(OutParameter outParameter) {
			return (RowId) outParameters.get(outParameter);
		}

		public Short getShort(OutParameter outParameter) {
			return (Short) outParameters.get(outParameter);
		}

		public String getString(OutParameter outParameter) {
			return (String) outParameters.get(outParameter);
		}

		public java.sql.Time getTime(OutParameter outParameter) {
			return (java.sql.Time) outParameters.get(outParameter);
		}

		public java.sql.Timestamp getTimestamp(OutParameter outParameter) {
			return (java.sql.Timestamp) outParameters.get(outParameter);
		}

		public Map<OutParameter, Object> getOutParameters() {
			return outParameters;
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
