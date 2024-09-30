package MIR.Instruction;

import MIR.IREntity.entity;
public class retInst extends Instruction {
    public entity retContent;
    public entity retValue = null;
    public retInst(entity retContent) {
        this.retContent = retContent;
    }
}
