package net.relaxism.utils.jdbc.procedure;

import lombok.*;
import net.relaxism.utils.jdbc.parameter.InParameter;
import net.relaxism.utils.jdbc.parameter.OutParameter;
import net.relaxism.utils.jdbc.parameter.Parameter;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class StoredProcedure {

    @Getter
    protected final String name;

    protected void registerParameters(final CallableStatement statement,
                                      final int startIndex,
                                      final Object... parameters) throws SQLException {
        int index = startIndex;
        for (val parameter : parameters) {
            if (parameter instanceof OutParameter) {
                val outParameter = (Parameter) parameter;
                if (outParameter.getScaleOrLength() == null) {
                    statement.registerOutParameter(index, outParameter.getType().getTypeCode());
                } else {
                    statement.registerOutParameter(index, outParameter.getType().getTypeCode(), outParameter.getScaleOrLength());
                }
            } else if (parameter instanceof InParameter) {
                val inParameter = (InParameter) parameter;
                if (inParameter.getScaleOrLength() == null) {
                    statement.setObject(index, inParameter.getValue(), inParameter.getType().getTypeCode());
                } else {
                    statement.setObject(index, inParameter.getValue(), inParameter.getType().getTypeCode(), inParameter.getScaleOrLength());
                }
            } else {
                statement.setObject(index, parameter);
            }
            index++;
        }
    }

    protected String generateParameterPlaceHolder(final int count) {
        if (count <= 0) {
            return "";
        }

        val holderChar = '?';

        return IntStream.rangeClosed(2, count)
            .mapToObj(i -> "," + holderChar)
            .collect(Collectors.joining("", String.valueOf(holderChar), ""));
    }

    protected static abstract class Return {

        @Getter
        protected final Map<OutParameter, Object> outParameters;

        public Return(final CallableStatement statement,
                      final int outParamStartIndex,
                      final Object... parameters) throws SQLException {
            this.outParameters = Collections.unmodifiableMap(processOutParameters(statement, outParamStartIndex, parameters));
        }

        private Map<OutParameter, Object> processOutParameters(final CallableStatement statement,
                                                               final int outParamStartIndex,
                                                               final Object... parameters) throws SQLException {
            val outParameters = new HashMap<OutParameter, Object>();
            int paramIndex = outParamStartIndex;
            for (val param : parameters) {
                if (param instanceof OutParameter) {
                    val outParameter = (OutParameter) param;
                    outParameters.put(outParameter, statement.getObject(paramIndex));
                }
                paramIndex++;
            }
            return outParameters;
        }

        public Array getArray(final OutParameter outParameter) {
            return (Array) outParameters.get(outParameter);
        }

        public BigDecimal getBigDecimal(final OutParameter outParameter) {
            return (BigDecimal) outParameters.get(outParameter);
        }

        public Blob getBlob(final OutParameter outParameter) {
            return (Blob) outParameters.get(outParameter);
        }

        public Boolean getBoolean(final OutParameter outParameter) {
            return (Boolean) outParameters.get(outParameter);
        }

        public Byte getByte(final OutParameter outParameter) {
            return (Byte) outParameters.get(outParameter);
        }

        public byte[] getBytes(final OutParameter outParameter) {
            return (byte[]) outParameters.get(outParameter);
        }

        public Clob getClob(final OutParameter outParameter) {
            return (Clob) outParameters.get(outParameter);
        }

        public java.sql.Date getDate(final OutParameter outParameter) {
            return (java.sql.Date) outParameters.get(outParameter);
        }

        public Double getDouble(final OutParameter outParameter) {
            return (Double) outParameters.get(outParameter);
        }

        public Float getFloat(final OutParameter outParameter) {
            return (Float) outParameters.get(outParameter);
        }

        public Integer getInteger(final OutParameter outParameter) {
            return (Integer) outParameters.get(outParameter);
        }

        public Long getLong(final OutParameter outParameter) {
            return (Long) outParameters.get(outParameter);
        }

        public Object getObject(final OutParameter outParameter) {
            return outParameters.get(outParameter);
        }

        public Ref getRef(final OutParameter outParameter) {
            return (Ref) outParameters.get(outParameter);
        }

        public RowId getRowId(final OutParameter outParameter) {
            return (RowId) outParameters.get(outParameter);
        }

        public Short getShort(final OutParameter outParameter) {
            return (Short) outParameters.get(outParameter);
        }

        public String getString(final OutParameter outParameter) {
            return (String) outParameters.get(outParameter);
        }

        public java.sql.Time getTime(final OutParameter outParameter) {
            return (java.sql.Time) outParameters.get(outParameter);
        }

        public java.sql.Timestamp getTimestamp(final OutParameter outParameter) {
            return (java.sql.Timestamp) outParameters.get(outParameter);
        }

    }

}
