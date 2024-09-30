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

    /*@Override
    public void removeUse(Entity entity) {
        if (lhs == entity) lhs = null;
        if (rhs == entity) rhs = null;
    }

    @Override
    public void replaceUse(Entity old, Entity new_) {
        if (lhs == old) lhs = new_;
        if (rhs == old) rhs = new_;
    }

    @Override
    public void replaceDef(Entity old, Entity new_) {
        if (result == old) result = new_;
    }

    @Override
    public String toString() {
        return result.toString() + " = icmp " + op.toString() + " " + lhs.type.toString() + " " + lhs.toString() + ", " + rhs.toString();
    }*/
}
