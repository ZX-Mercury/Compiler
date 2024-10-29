package MIR.Instruction;

import MIR.IREntity.entity;
import MIR.Value.*;

public class storeInst extends Instruction {
    public value value;
    public value pointer;

    public storeInst(value value, value pointer) {
        this.value = value;
        this.pointer = pointer;
    }
}
