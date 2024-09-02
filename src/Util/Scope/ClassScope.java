package Util.Scope;

import AST.Definition.*;

import java.util.HashMap;
import java.util.Map;

public class ClassScope extends Scope {
    public HashMap<String, funcDefNode> funcMember;
    public ClassScope(Scope parent) {
        super(parent);
        funcMember = new HashMap<>();
    }
}
