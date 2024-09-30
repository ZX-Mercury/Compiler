package MIR.Instruction;

import MIR.IREntity.entity;
import MIR.IREntity.ptr;

public class allocaInst extends Instruction {
    public ptr dest;
    public entity allocaType;
    public allocaInst(ptr dest, entity allocaType) {
        this.dest = dest;
        this.allocaType = allocaType;
    }
}
