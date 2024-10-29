package MIR.Instruction;

import MIR.IREntity.entity;
import MIR.Value.value;

public class retInst extends Instruction {
    public value retValue = null;

    public retInst(value retValue) {
        this.retValue = retValue;
    }
}
