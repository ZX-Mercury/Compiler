package MIR.Instruction;

import MIR.Value.*;

public class allocaInst extends Instruction {
    public varLocal dest;

    public allocaInst(varLocal dest) {
        this.dest = dest;
    }
}
