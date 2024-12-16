package MIR.Instruction;

import MIR.Instruction.Instruction;
import MIR.Instruction.*;
import MIR.IREntity.*;
import MIR.Value.*;

import java.util.ArrayList;

public class phiInst extends Instruction {
    public localVar result;
    public ArrayList<value> values;
    public ArrayList<block> blocks;
    public phiInst(localVar result) {
        this.result = result;
        values = new ArrayList<>();
        blocks = new ArrayList<>();
    }
    @Override
    public String toStr() {
        return "";
    }
}