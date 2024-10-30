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
}
