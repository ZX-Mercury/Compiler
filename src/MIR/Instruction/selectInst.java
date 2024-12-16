package MIR.Instruction;
import MIR.Instruction.Instruction;
import MIR.IREntity.*;
import MIR.Value.*;

public class selectInst extends Instruction {
    public varLocal ret;
    public value cond;
    public value op1, op2;
    public selectInst(varLocal ret, value obj1, value obj2, value obj3) {
        this.ret = ret;
        this.cond = obj1;
        this.op1 = obj2;
        this.op2 = obj3;
    }

    @Override
    public String toStr() {
        return "";
    }
    /*@Override
    public String toString() {
        return ret.toString()+" = select "+cond.type+" "+cond.getName()+", "+op1.type+" "+op1.getName()+", "+op2.type+" "+op2.getName();
    }*/
}
