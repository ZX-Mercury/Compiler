package MIR.Value;

import MIR.Type.*;

public class varGlobal extends value {
    public String name;

    public varGlobal(String name, type type) {
        super(type);
        this.name = name;
    }
}
