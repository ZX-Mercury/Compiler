package MIR.Instruction;

import MIR.Instruction.Instruction;
import MIR.Instruction.*;
import MIR.IREntity.*;

import java.util.ArrayList;

public class phiInst extends Instruction {
    public entity result;
    public ArrayList<localVar> values;
    public ArrayList<block> blocks;
    public phiInst(entity result) {
        this.result = result;
        values = new ArrayList<>();
        blocks = new ArrayList<>();
    }

}