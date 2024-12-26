package MIR;

import AST.RootNode;
import MIR.IREntity.*;
import MIR.Instruction.Instruction;
import MIR.Value.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import MIR.Type.intType;
import Util.Scope.*;

import static MIR.Type.type.toIRType;

public class IRPrinter {
    private PrintStream out;
    private globalScope globalScope;
    public IRPrinter(PrintStream out, globalScope globalScope) {
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
            var v = globalScope.entities.get(s);
            if(v.valueType instanceof intType) {
                int init_value = 0;
                if(((varGlobal)v).init instanceof constInt){
                    init_value=((constInt) ((varGlobal)v).init).value;
                }
                out.println("@" + s + " = global i" + ((intType) (v.valueType)).bitLen + " "+init_value);
            }
        }
        for (var funcname : globalScope.funcMember.keySet()) {
            ArrayList<String> builtin = new ArrayList<>(Arrays.asList("getInt", "print", "println", "printInt", "printlnInt", "toString", "getString"));
            if (builtin.contains(funcname)) continue;
            var func = globalScope.funcMember.get(funcname);
            out.print("define ");
            var tp = toIRType(func.retType);
            if (tp instanceof intType) {
                out.print("i" + ((intType) tp).bitLen + " ");
            } else {
                out.print("void ");
            }
            out.print("@" + funcname + "(");
            /*for (int i = 0; i < func.parameterList.size(); i++) {
                var para = func.parameters.get(i);
                if (para.type instanceof intType) {
                    out.print("i" + ((intType) para.type).bitLen + " ");
                } else {
                    out.print("void ");
                }
                out.print("%" + para.name);
                if (i != func.parameters.size() - 1) out.print(", ");
            }*/
            out.println(") {");
            for (Instruction inst : func.blk.insts()) {
                System.out.println(inst.toStr());
            }
            //visitBlock(func.blocks.get(0));
            out.println("}");
        }
        /*for (var block : t.blocks) {
            visitBlock(block);
        }*/
    }

    public void visitStmtNode(){
    }

    /*public void visitBlock(Block b){

    }*/
    /*public void visitStmtNode(){
    }*/
}
