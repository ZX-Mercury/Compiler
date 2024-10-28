package MIR.Value;

import MIR.Type.intType;

public class constInt extends value {
    public int value;

    public constInt(int value) {
        super(new intType(32));
        this.value = value;
    }

}
