package MIR.Instruction;

import MIR.IREntity.block;

// jumpInst is a specific case of branchInst, there is no condition
public class jumpInst extends terminalInst {
    public block dest;

    public jumpInst(block dest) {
        this.dest = dest;
    }

    @Override
    public String toStr() {
        return "";
    }
    /*@Override
    public String toString() {
        return "jump " + dest.toString();
    }*/
}
