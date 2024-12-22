package MIR.Value;

import MIR.Type.ptrType;

public class constNull extends value {
    public constNull() {
        super(new ptrType());
    }

    public String toStr() {
        return "6969null";
    }
}
