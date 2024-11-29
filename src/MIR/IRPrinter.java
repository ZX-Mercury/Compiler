package MIR;

import AST.RootNode;
import MIR.IREntity.block;
import MIR.Instruction.Instruction;

import java.io.PrintStream;

import MIR.Type.intType;
import Util.Scope.*;

public class IRPrinter {
    private PrintStream out;
    private Scope globalScope;
    public IRPrinter(PrintStream out, Scope globalScope) {
        this.out = out;
        this.globalScope = globalScope;
    }
    public void visitInst(Instruction t) {}
    public void visitBlock(block t) {
        out.println(t.name + ": ");
        t.insts().forEach(this::visitInst);
        t.successors().forEach(this::visitBlock);
    }

    public void visitRoot(RootNode t) {
        for (String s: globalScope.entities.keySet()){
            out.println("%" +s+"=global i" + ((intType)(globalScope.entities.get(s).valueType)).bitLen);
        }
        for (var block : t.blocks) {
            visitBlock(block);
        }
    }

    public void visitStmtNode(){
    }
}
