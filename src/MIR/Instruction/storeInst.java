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

    @Override
    public String toStr() {
        return "store " + value.valueType.toStr() + " " + value.toStr() + ", ptr " + pointer.toStr();
    }
}
