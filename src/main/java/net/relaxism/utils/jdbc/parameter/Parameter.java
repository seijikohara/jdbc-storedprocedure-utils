package net.relaxism.utils.jdbc.parameter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.relaxism.utils.jdbc.SQLType;

@EqualsAndHashCode
@ToString
public abstract class Parameter {

    @Getter
    protected final SQLType type;

    @Getter
    protected final Integer scaleOrLength;

    public Parameter(SQLType type) {
        this(type, null);
    }

    public Parameter(SQLType type, Integer scaleOrLength) {
        this.type = type;
        this.scaleOrLength = scaleOrLength;
    }

}
