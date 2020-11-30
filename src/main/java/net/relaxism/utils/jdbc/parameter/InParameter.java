package net.relaxism.utils.jdbc.parameter;

import lombok.Getter;
import net.relaxism.utils.jdbc.SQLType;

public class InParameter extends Parameter {

    @Getter
    private final String value;

    public InParameter(String value, SQLType type) {
        this(value, type, null);
    }

    public InParameter(String value, SQLType type, Integer scaleOrLength) {
        super(type, scaleOrLength);
        this.value = value;
    }

}
