package MIR.Value;

import MIR.Type.*;

public class varGlobal extends value {
    public String name;
    public value init;

    public varGlobal(String name, type type) {
        super(type);
        this.name = name;
    }

    @Override
    public String toStr() {
        return name;
    }
}
