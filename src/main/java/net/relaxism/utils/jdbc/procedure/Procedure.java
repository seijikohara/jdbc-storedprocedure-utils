package net.relaxism.utils.jdbc.procedure;

import lombok.val;
import net.relaxism.utils.jdbc.SQLRuntimeException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Procedure extends StoredProcedure {

    private static final int PARAMETER_START_INDEX = 1;

    public Procedure(final String name) {
        super(name);
    }

    public Return execute(final Connection connection, final Object... parameters) {
        val paramPlaceHolder = generateParameterPlaceHolder(parameters.length);
        val sql = String.format("{ call %s(%s) }", name, paramPlaceHolder);

        try {
            val statement = connection.prepareCall(sql);
            registerParameters(statement, PARAMETER_START_INDEX, parameters);
            statement.executeQuery();
            return new Return(statement, parameters);
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    public static class Return extends StoredProcedure.Return {

        public Return(final CallableStatement statement, final Object... parameters) throws SQLException {
            super(statement, PARAMETER_START_INDEX, parameters);
        }

    }

}
