package MIR.Instruction;

import MIR.IREntity.*;
public class binaryInst extends Instruction{
    public enum binaryOp{
        add, sub, mul, sdiv, srem, shl, ashr, and, or, xor
    }
    public binaryOp op;
    public register lhs;
    public entity op1, op2;

    public binaryInst(register lhs, entity op1, entity op2, binaryOp op) {
        super();
        this.lhs = lhs;
        this.op1 = op1;
        this.op2 = op2;
        this.op = op;
    }

    /*@Override
    public String toString() {
        return "%" + dest + " = " + op + " " + lhs.type + " " + lhs + ", " + rhs;
    }*/
}
