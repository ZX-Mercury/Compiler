package MIR.Instruction;

import java.util.ArrayList;
import java.util.Arrays;

import MIR.Value.*;

import java.util.ArrayList;

//<result> = call <ResultType> @<FunctionName>(<arguments>)
//call void @<FunctionName>(<arguments>)
public class callInst extends Instruction {
    public varLocal ret;
    public String funcName;
    public ArrayList<value> para;

    public callInst(varLocal ret, String funcName, value ... args) {
        this.ret = ret;
        this.funcName = funcName;

        para = new ArrayList<>();
        para.addAll(Arrays.asList(args));
    }
    @Override
    public String toStr() {
        return "";
    }
}
