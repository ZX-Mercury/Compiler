package MIR.Value;

import MIR.Type.intType;

public class constBool extends value {
    public boolean value;

    public constBool(boolean value) {
        super(new intType(1));
        this.value = value;
    }
}
