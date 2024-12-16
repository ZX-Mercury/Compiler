package MIR.Instruction;

import MIR.IREntity.entity;
import MIR.Value.value;

import java.io.PrintStream;

public class retInst extends Instruction {
    public value retValue = null;

    public retInst(value retValue) {
        this.retValue = retValue;
    }

    @Override
    public String toStr() {
        if (retValue == null) return "ret void";
        else return "ret " + retValue.valueType.toString() + " " + retValue.toString();
    }
}
