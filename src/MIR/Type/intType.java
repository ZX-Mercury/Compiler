package MIR.Type;

import Util.Type;

public class intType extends type{
    public int bitLen;

    public intType(int bitLen) {
        this.bitLen = bitLen;
    }

    @Override
    public String toStr() {
        return "i" + bitLen;
    }
}
