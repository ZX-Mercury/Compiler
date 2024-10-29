package MIR.Instruction;

import MIR.Value.*;

public class loadInst extends Instruction {
    public varLocal result;
    public value pointer;
    public loadInst(varLocal result, value pointer) {
        this.result = result;
        this.pointer = pointer;
    }
}