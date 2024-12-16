package MIR.Instruction;

import MIR.IREntity.*;
import MIR.Value.*;

import java.util.ArrayList;

public class getelementptrInst extends Instruction{
    public varLocal res;
    public value ptr;
    public value id1;
    public int id2;

    public getelementptrInst(varLocal res, value ptr, value id1) {
        this.res = res;
        this.ptr = ptr;
        this.id1 = id1;
        this.id2 = -1;
    }

    public getelementptrInst(varLocal res, value ptr, int id2) {
        this.res = res;
        this.ptr = ptr;
        id1 = null;
        this.id2 = id2;
    }
    @Override
    public String toStr() {
        return "";
    }
}
