package MIR.Instruction;

import MIR.IREntity.entity;

public class storeInst extends Instruction {
    public entity value;
    public entity pointer;
    public storeInst(entity value, entity pointer) {
        this.value = value;
        this.pointer = pointer;
    }
}
