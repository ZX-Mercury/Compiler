package MIR.Value;

import MIR.Type.type;

public abstract class value {
    public type valueType;
    public value(type valueType) {
        this.valueType = valueType;
    }
}
