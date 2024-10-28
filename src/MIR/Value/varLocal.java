package MIR.Value;

import MIR.Type.*;

public class varLocal extends value {
    public String name;

    public varLocal(String name, type type) {
        super(type);
        this.name = name;
    }
}
