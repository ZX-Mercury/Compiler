package MIR.Instruction;

import MIR.Value.*;

public class icmpInst extends Instruction {
    public enum icmpOpType {
        eq, ne,
        ugt, uge, ult, ule,
        sgt, sge, slt, sle
    }

    public varLocal result;
    public icmpOpType op;
    public value lhs, rhs;

    public icmpInst(varLocal result, icmpOpType op, value lhs, value rhs) {
        this.result = result;
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

/*
    @Override
    public String toString() {
        return result.toString() + " = icmp " + op.toString() + " " + lhs.type.toString() + " " + lhs.toString() + ", " + rhs.toString();
    }*/
}
