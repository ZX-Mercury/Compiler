package MIR.Instruction;

import MIR.IREntity.entity;

public class icmpInst extends Instruction {
    public enum icmpOpType {
        eq, ne,
        ugt, uge, ult, ule,
        sgt, sge, slt, sle
    }

    public entity result;
    public icmpOpType op;
    public entity lhs, rhs;

    public icmpInst(entity result, icmpOpType op, entity lhs, entity rhs) {
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
