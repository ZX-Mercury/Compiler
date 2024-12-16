package MIR.Instruction;

import MIR.Value.*;

import java.io.PrintStream;

public class allocaInst extends Instruction {
    public varLocal dest;

    public allocaInst(varLocal dest) {
        this.dest = dest;
    }

    @Override
    public String toStr() {
        return "";
    }
}
