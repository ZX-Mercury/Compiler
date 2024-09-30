package MIR.Instruction;

import MIR.IREntity.entity;
//import
public class loadInst extends Instruction {
    public entity result;
    public entity pointer;
    public loadInst(entity result, entity pointer) {
        this.result = result;
        this.pointer = pointer;
    }
}
