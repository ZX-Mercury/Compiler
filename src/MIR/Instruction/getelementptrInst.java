package MIR.Instruction;

import MIR.IREntity.*;

import java.util.ArrayList;

public class getelementptrInst extends Instruction{
    public localPtr result;
    public ptr ptrVal;
    //public ArrayList<entity> index;
    public getelementptrInst(localPtr result, ptr ptrVal) {
        this.result = result;
        this.ptrVal = ptrVal;
        //this.index = new ArrayList<>();
    }
}
