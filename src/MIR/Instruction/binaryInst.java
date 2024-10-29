package MIR.Instruction;

import MIR.Value.*;

public class binaryInst extends Instruction{
    public enum binaryOp{
        add, sub, mul, sdiv, srem, shl, ashr, and, or, xor
    }
    public binaryOp op;
    public varLocal result;
    public value op1, op2;

    public binaryInst(varLocal result, value op1, value op2, binaryOp op) {
        super();
        this.result = result;
        this.op1 = op1;
        this.op2 = op2;
        this.op = op;
    }

    /*@Override
    public String toString() {
        return "%" + dest + " = " + op + " " + lhs.type + " " + lhs + ", " + rhs;
    }*/
}
