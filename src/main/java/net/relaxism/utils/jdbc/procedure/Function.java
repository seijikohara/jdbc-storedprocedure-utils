package net.relaxism.utils.jdbc.procedure;

import net.relaxism.utils.jdbc.SQLRuntimeException;
import net.relaxism.utils.jdbc.SQLType;

import java.math.BigDecimal;
import java.sql.*;

public class Function extends StoredProcedure {

    private static final int PARAMETER_START_INDEX = 2;

    public Function(String name) {
        super(name);
    }

    public Return execute(Connection connection, SQLType returnType,
                          Object... parameters) {
        String paramPlaceHolder = generateParameterPlaceHolder(parameters.length);
        String sql = String.format("{ ? = call %s(%s) }", name,
            paramPlaceHolder);
        try {
            CallableStatement statement = connection.prepareCall(sql);
            statement.registerOutParameter(1, returnType.getTypeCode());
            registerParameters(statement, PARAMETER_START_INDEX, parameters);
            statement.executeQuery();
            return new Return(statement, returnType, parameters);
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    public static class Return extends StoredProcedure.Return {

        public final SQLType returnType;
        public final Object returnValue;

        public Return(CallableStatement statement, SQLType returnType,
                      Object... parameters) throws SQLException {
            super(statement, PARAMETER_START_INDEX, parameters);
            this.returnType = returnType;
            this.returnValue = statement.getObject(1);
        }

        public SQLType getReturnType() {
            return returnType;
        }

        public Object getReturnObject() {
            return returnValue;
        }

        public Array getReturnArray() {
            return (Array) returnValue;
        }

        public BigDecimal getReturnBigDecimal() {
            return (BigDecimal) returnValue;
        }

        public Blob getReturnBlob() {
            return (Blob) returnValue;
        }

        public Boolean getReturnBoolean() {
            return (Boolean) returnValue;
        }

        public Byte getReturnByte() {
            return (Byte) returnValue;
        }

        public byte[] getReturnBytes() {
            return (byte[]) returnValue;
        }

        public Clob getReturnClob() {
            return (Clob) returnValue;
        }

        public java.sql.Date getReturnDate() {
            return (java.sql.Date) returnValue;
        }

        public Double getReturnDouble() {
            return (Double) returnValue;
        }

        public Float getReturnFloat() {
            return (Float) returnValue;
        }

        public Integer getReturnInteger() {
            return (Integer) returnValue;
        }

        public Long getReturnLong() {
            return (Long) returnValue;
        }

        public Ref getReturnRef() {
            return (Ref) returnValue;
        }

        public RowId getReturnRowId() {
            return (RowId) returnValue;
        }

        public Short getReturnShort() {
            return (Short) returnValue;
        }

        public String getReturnString() {
            return (String) returnValue;
        }

        public java.sql.Time getReturnTime() {
            return (java.sql.Time) returnValue;
        }

        public java.sql.Timestamp getReturnTimestamp() {
            return (java.sql.Timestamp) returnValue;
        }

    }

}
