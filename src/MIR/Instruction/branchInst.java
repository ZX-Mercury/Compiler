package MIR.Instruction;

import MIR.IREntity.block;
import MIR.Value.value;

public class branchInst extends terminalInst {
    public value cond;
    public block trueBranch, falseBranch;

    public branchInst(value cond, block trueBranch, block falseBranch) {
        this.cond = cond;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

/*
    @Override
    public String toString() {
        return "br " + cond.toString() + ", " + then.toString() + ", " + els.toString();
    }*/
}
