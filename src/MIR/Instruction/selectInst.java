package MIR.Instruction;
import MIR.Instruction.Instruction;
import MIR.IREntity.*;

public class selectInst extends Instruction {
    public localVar ret;
    public entity cond;
    public entity op1, op2;
    public selectInst(entity obj1, entity obj2, entity obj3, String name) {
        cond = obj1;
        op1 = obj2;
        op2 = obj3;
    }


    /*@Override
    public String toString() {
        return ret.toString()+" = select "+cond.type+" "+cond.getName()+", "+op1.type+" "+op1.getName()+", "+op2.type+" "+op2.getName();
    }*/
}
