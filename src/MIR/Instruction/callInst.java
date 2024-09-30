package MIR.Instruction;

import MIR.IREntity.*;

import java.util.ArrayList;

public class callInst extends Instruction {
    public localVar ret;
    public String funcName;
    public ArrayList<entity> para;
    public callInst(function func, String name) {
        para = new ArrayList<>();
        if (!func.paraList.isEmpty()) {
            para.addAll(func.paraList);
        }
        funcName = func.IRname;
    }
}
