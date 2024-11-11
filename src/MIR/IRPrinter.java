package MIR;

import AST.RootNode;
import MIR.IREntity.block;
import MIR.Instruction.Instruction;

import java.io.PrintStream;

public class IRPrinter {
    private PrintStream out;
    public IRPrinter(PrintStream out) {
        this.out = out;
    }
    public void visitInst(Instruction t) {}
    public void visitBlock(block t) {
        out.println(t.name + ": ");
        t.insts().forEach(this::visitInst);
        t.successors().forEach(this::visitBlock);
    }

    public void visitRoot(RootNode t) {
        for (var block : t.blocks) {
            visitBlock(block);
        }
    }

    public void visitStmtNode(){
    }
}
