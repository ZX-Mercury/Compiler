package MIR.IREntity;

import MIR.IREntity.*;
import MIR.Type.type;
import MIR.Value.*;

import java.util.ArrayList;

public class function extends entity {
    public type retType;
    public String name;
    public ArrayList<varLocal> args;
    public ArrayList<block> body;

    public function(String name, type retType) {
        this.name = name;
        this.retType = retType;
        this.args = new ArrayList<>();
        this.body = new ArrayList<>();
    }

    public String declare(){
        StringBuilder res = new StringBuilder("declare ");
        res.append(retType.toString()).append(" @").append(name).append("(");
        for (int i = 0; i < args.size(); i++) {
            res.append(args.get(i).valueType.toString()).append(" ").append(args.get(i).name);
            if (i != args.size() - 1) res.append(", ");
        }
        res.append(")");
        return res.toString();
    }
}
