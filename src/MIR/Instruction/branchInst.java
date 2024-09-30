package MIR.Instruction;

import MIR.IREntity.entity;
import MIR.IREntity.block;
public class branchInst extends terminalInst {
    public entity cond;
    public block trueBranch, falseBranch;

    public branchInst(entity cond, block trueBranch, block falseBranch) {
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
