package MIR.Instruction;

import MIR.Value.*;

public class icmpInst extends Instruction {
    public enum icmpOpType {
        eq, ne,             //=, !=
        ugt, uge, ult, ule, //unsigned >, >=, <, <=
        sgt, sge, slt, sle  //signed >, >=, <, <=
    }

    public varLocal result;
    public icmpOpType op;
    public value lhs, rhs;

    public icmpInst(varLocal result, value lhs, value rhs, icmpOpType op) {
        this.result = result;
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }
    @Override
    public String toStr() {
        return "";
    }
/*
    @Override
    public String toString() {
        return result.toString() + " = icmp " + op.toString() + " " + lhs.type.toString() + " " + lhs.toString() + ", " + rhs.toString();
    }*/
}
