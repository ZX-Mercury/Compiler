package Util.Scope;

import Util.error.semanticError;
import Util.position;
import Util.Type;
public class FuncScope extends Scope{
    public Type returnType = null;
    public boolean returned=false;
    public FuncScope(Scope parent, Type ret){
        super(parent);
        this.returnType = ret;
    }
    public void checkReturn(Type ret, position pos) {
        if (ret.btype.equals(Type.basicType.This) && returnType.Identifier.equals(ret.Identifier)) return;
        if (returnType.dim!=0 && ret.btype== Type.basicType.Null) return;
        if (!returnType.btype.equals(ret.btype)) throw new semanticError("Type Mismatch", pos);
    }
}
