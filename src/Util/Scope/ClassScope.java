package Util.Scope;

import AST.Definition.*;
import Util.Type;

import java.util.HashMap;
import java.util.Map;

public class ClassScope extends Scope {
    public HashMap<String, funcDefNode> funcMember;
    public ClassScope(Scope parent) {
        super(parent);
        funcMember = new HashMap<>();
    }
    public funcDefNode getFuncType(String name, boolean lookUpon) {
        if (funcMember.containsKey(name)) return funcMember.get(name);
        else if (parentScope != null && parentScope() instanceof ClassScope && lookUpon)
            return ((ClassScope)parentScope).getFuncType(name, true);
        return null;
    }
}
