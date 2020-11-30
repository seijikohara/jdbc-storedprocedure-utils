package net.relaxism.utils.jdbc.parameter;

import lombok.Getter;
import net.relaxism.utils.jdbc.SQLType;

public class OutParameter extends Parameter {

    @Getter
    private final String name;

    public OutParameter(String name, SQLType type) {
        this(name, type, null);
    }

    public OutParameter(String name, SQLType type, Integer scaleOrLength) {
        super(type, scaleOrLength);
        this.name = name;
    }

}
