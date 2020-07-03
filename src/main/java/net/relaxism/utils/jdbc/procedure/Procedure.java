package net.relaxism.utils.jdbc.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import net.relaxism.utils.jdbc.SQLRuntimeException;

public class Procedure extends StoredProcedure {

	private static final int PARAMETER_START_INDEX = 1;

	public Procedure(String name) {
		super(name);
	}

	public Return execute(Connection connection, Object... parameters) {
		String paramPlaceHolder = generateParameterPlaceHolder(parameters.length);
		String sql = String.format("{ call %s(%s) }", name, paramPlaceHolder);

		try {
			CallableStatement statement = connection.prepareCall(sql);
			registerParameters(statement, PARAMETER_START_INDEX, parameters);
			statement.executeQuery();
			return new Return(statement, parameters);
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	public static class Return extends StoredProcedure.Return {

		public Return(CallableStatement statement, Object... parameters)
				throws SQLException {
			super(statement, PARAMETER_START_INDEX, parameters);
		}

	}

}
