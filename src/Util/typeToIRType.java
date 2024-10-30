package Util;

public class typeToIRType {
    private typeToIRType () {

    }

    public static String toIR(Type t){
        if(t.btype == Type.basicType.Int) return "i32";
        else if(t.btype == Type.basicType.Bool) return "i1";
        else return "ptr";
    }
}
