package MIR.IREntity;

import Util.error.internalError;
import Util.position;

import MIR.Instruction.Instruction;
import MIR.Instruction.terminalInst;
import MIR.Instruction.branchInst;
import java.util.ArrayList;

public class block extends entity {
    public String name;
    private ArrayList<Instruction> insts = new ArrayList<>();
    private terminalInst tailInst = null;
    public block() {}
    public void push_back(Instruction inst) {
        insts.add(inst);
        if (inst instanceof terminalInst) {
            if (tailInst != null)
                throw new internalError("multiple tails of a block",
                        new position(0, 0));
            tailInst = (terminalInst)inst;
        }
    }
    public ArrayList<Instruction> insts() {
        return new ArrayList<>(insts);
    }
    public ArrayList<block> successors() {
        ArrayList<block> ret = new ArrayList<>();
        if (tailInst instanceof branchInst) {
            ret.add(((branchInst) tailInst).trueBranch);
            ret.add(((branchInst) tailInst).falseBranch);
        }
        /*else if (tailInst instanceof jump) {
            ret.add(((jump) tailInst).destination);
        }*/
        return ret;
    }
}
