package MIR.Instruction;

import MIR.Instruction.Instruction;
import MIR.Instruction.*;
import MIR.IREntity.*;

import java.util.ArrayList;

public class phiInst extends Instruction {
    /*public entity result;
    public ArrayList<localVar> values;
    public ArrayList<Block> blocks;
    public phiInst(entity result) {
        this.result = result;
        values = new ArrayList<>();
        blocks = new ArrayList<>();
    }*/



    /*public void addBranch(localVar value, Block block) {
        values.add(value);
        blocks.add(block);
    }
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(result.toString()+" = phi "+result.type+" ");
        for (int i = 0; i < values.size(); i++) {
            ret.append("[ ").append(values.get(i).type).append(" ").append(values.get(i).getName()).append(", ").append(blocks.get(i).getName()).append(" ]");
            if (i != values.size()-1) ret.append(", ");
        }
        return ret.toString();
    }*/

}